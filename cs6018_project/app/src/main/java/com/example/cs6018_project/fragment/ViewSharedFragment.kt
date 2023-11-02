package com.example.cs6018_project.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cs6018_project.DynamicConfig
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ViewSharedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ViewSharedFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    val mainScope = MainScope()
    val client = HttpClient(CIO)

    var flagLoading : MutableState<Boolean> = mutableStateOf(true)
    var currentDisplayList = mutableStateListOf<String>()
    var currentMode : MutableState<String> = mutableStateOf("loading")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        mainScope.launch {
            val response = client.get("http://10.0.2.2:8080/list_user_painting/" + DynamicConfig.userEmail)
            currentDisplayList.clear()
            val responseList: List<String> = Gson().fromJson(
                response.bodyAsText(),
                object: TypeToken<List<String>>() {}.type
            )
            responseList.forEach {
                currentDisplayList.add(it)
            }
            flagLoading.value = false
            currentMode.value = "list_painting"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return ComposeView(requireContext()).apply{
            setContent {
                createView()
            }
        }
    }

    @Composable
    fun createView() {
        if(flagLoading.value) {
            return Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    CircularProgressIndicator(
                        modifier = Modifier.width(64.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        trackColor = MaterialTheme.colorScheme.secondary,
                    )
                }
            )
        }
        else {
            return LazyColumn {
                items(currentDisplayList) {
                        item ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .clip(RoundedCornerShape(10.dp))
                            .padding(horizontal = 10.dp, vertical = 15.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(bounded = false),
                                onClick = {
                                    when (currentMode.value) {
                                        "loading" -> {}
                                        "list_painting" -> {
                                            deletePainting(item)
                                        }
                                    }
                                }
                            )
                    ) {
                        Text(text = item, modifier = Modifier.padding(16.dp), fontSize = 16.sp)
                    }
                }
            }
        }
    }

    fun doDeletePainting(paintingName: String) {
        mainScope.launch {
            val response = client.delete("http://10.0.2.2:8080/remove_image/$paintingName") {
                headers.append("email", DynamicConfig.userEmail.orEmpty())
                headers.append("uid", DynamicConfig.userId.orEmpty())
            }

            Log.wtf("%%%", "Delete response: " + response)

            currentDisplayList.remove(paintingName)
            flagLoading.value = false
        }

    }

    fun deletePainting(paintingName: String) {
        mainScope.launch {
            Log.wtf("^^^", "Start launch")

            flagLoading.value = true

            displayDialog(paintingName)

        }
    }

    fun displayDialog(paintingName: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete painting")
            .setMessage("Are you sure you want to delete this painting?")
            .setPositiveButton(android.R.string.yes,
                DialogInterface.OnClickListener { dialog, which ->
                    doDeletePainting(paintingName)
                }) // A null listener allows the button to dismiss the dialog and take no further action.
            .setNegativeButton(android.R.string.no,
                DialogInterface.OnClickListener { dialog, which ->
                    flagLoading.value = false
                })
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ViewSahredFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ViewSharedFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}