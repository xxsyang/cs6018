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
import java.io.FileOutputStream
import java.io.IOException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



/**
 * A simple [Fragment] subclass.
 * Use the [SavedBoardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SavedBoardFragment : Fragment() {
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

    private fun jumpToBoard(fileName: String) {
        Log.wtf("*", "Text clicked: $fileName")

        var localFileName = fileName

        if(localFileName == "New board") {
            val datetime = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")
            val newFileName = "Board_" + datetime.format(formatter) + ".png"
            val newBoard = Bitmap.createBitmap(2160, 3840, Bitmap.Config.ARGB_8888)
            var itemDao = DynamicConfig.database.itemDao()
            try {
                var baos = ByteArrayOutputStream()
                newBoard.compress(Bitmap.CompressFormat.PNG, 95,baos)
                itemDao.insertAll(Item(name=newFileName, image=baos.toByteArray()))

            } catch (e: IOException) {
                e.printStackTrace()
            }

            localFileName = newFileName
        }

        DynamicConfig.currentEditBoard = localFileName

        val navController = findNavController()
        navController.navigate(R.id.action_savedBoardFragment_to_secondFragment)
    }

    @Composable
    fun createView(fileNames: List<String>) {
        LazyColumn {
            items(fileNames) {
                    fileName ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clip(RoundedCornerShape(10.dp))
                        .padding(horizontal = 10.dp, vertical = 15.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = false),
                            onClick = { jumpToBoard(fileName) }
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

    private fun getFileListFS(): List<String> {
        val dir = File(DynamicConfig.savedBoardDirectory)
        dir.list().iterator().forEach {
            Log.wtf("*", it)
        }
        return dir.list().asList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply{
            setContent {
                Text(
                    text = "Welcome, " + "username" + "!", // TODO: username should be passed from login fragment -- Jinyi
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .padding(10.dp),
                    fontSize = 16.sp
                )
                createView(listOf("New board") + getFileList())
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
            SavedBoardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}