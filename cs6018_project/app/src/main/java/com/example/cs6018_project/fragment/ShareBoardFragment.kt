package com.example.cs6018_project.fragment

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cs6018_project.DynamicConfig
import com.example.cs6018_project.R
import com.example.cs6018_project.room.Item
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ShareBoardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShareBoardFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private fun shareBoard(fileName: String) {
        Log.wtf("*", "Text clicked: $fileName")
    }

    @Composable
    fun createView(fileNames: List<String>) {
        LazyColumn {
            items(fileNames) { fileName ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clip(RoundedCornerShape(10.dp))
                        .padding(horizontal = 10.dp, vertical = 15.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = false),
                            onClick = { shareBoard(fileName) }
                        )
                ) {
                    Text(text = fileName, modifier = Modifier.padding(16.dp), fontSize = 16.sp)
                }
            }
        }
    }

    private fun getFileList(): List<String> {
        val items = DynamicConfig.database.itemDao().getAll()
        var result = ArrayList<String>()

        items.forEach {
            it.name?.let { itx -> result.add(itx) }
        }

        return result.toList()
    }

//    private fun getFileListFS(): List<String> {
//        val dir = File(DynamicConfig.savedBoardDirectory)
//        dir.list().iterator().forEach {
//            Log.wtf("*", it)
//        }
//        return dir.list().asList()
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply{
            setContent {
                createView(getFileList())
            }
        } //inflater.inflate(R.layout.fragment_saved_board, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SavedBoardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic fun newInstance(param1: String, param2: String) =
            ShareBoardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}