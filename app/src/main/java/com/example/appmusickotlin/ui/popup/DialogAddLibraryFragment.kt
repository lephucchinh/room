package com.example.appmusickotlin.ui.diaglogs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appmusickotlin.R
import com.example.appmusickotlin.ui.adapter.AlbumAdapter
import com.example.appmusickotlin.ui.home.HomeScreenActivity
import com.example.appmusickotlin.databinding.FragmentDialogAddLibraryBinding
import com.example.appmusickotlin.model.Song
import com.example.appmusickotlin.model.User
import com.example.appmusickotlin.ui.home.Fragment.PlayListsFragment
import com.example.appmusickotlin.util.callBack.OnItemClickListener
import com.example.appmusickotlin.viewmodel.PlaylistViewModel
import com.example.appmusickotlin.viewmodel.UserViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class DialogAddLibraryFragment : DialogFragment(), OnItemClickListener {

    private lateinit var binding: FragmentDialogAddLibraryBinding
    private lateinit var playlistViewModel : PlaylistViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogAddLibraryBinding.inflate(layoutInflater)
        playlistViewModel = ViewModelProvider(requireActivity()).get(PlaylistViewModel::class.java)
        playlistViewModel.getPlaylist(User.userId!!)
        playlistViewModel.playlist.observe(requireActivity(), Observer { playlist ->
            if(playlist.isNullOrEmpty()){
                binding.groupTextViews.visibility = View.VISIBLE
                binding.rccAlbum.visibility = View.GONE
            } else {
                binding.groupTextViews.visibility = View.GONE
                binding.rccAlbum.visibility = View.VISIBLE
                val adapter = AlbumAdapter(playlist,this)
                binding.rccAlbum.layoutManager = LinearLayoutManager(requireContext())
                binding.rccAlbum.adapter = adapter
            }
        })
//        val album = User.albumsLst
//        if(album.isNullOrEmpty()){
//            binding.groupTextViews.visibility = View.VISIBLE
//            binding.rccAlbum.visibility = View.GONE
//        } else {
//            binding.groupTextViews.visibility = View.GONE
//            binding.rccAlbum.visibility = View.VISIBLE
//            val adapter = AlbumAdapter(album,this)
//            binding.rccAlbum.layoutManager = LinearLayoutManager(requireContext())
//            binding.rccAlbum.adapter = adapter
//        }


        binding.btnAddmusic.setOnClickListener {
            val newFragment = PlayListsFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, newFragment)
                .addToBackStack(null)
                .commit()

            val navigationBottom = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
            navigationBottom.menu.findItem(R.id.btnPlaylist).setChecked(true)
            dismiss()
        }

        return  binding.root

    }

    override fun onItemClick(position: Int) {
        val song: Song? = arguments?.getSerializable("song") as? Song
        val selectedAlbum = User.albumsLst.get(position)
       // Kiểm tra nếu selectedAlbum không null và selectedAlbum.listMusic là null
        if (selectedAlbum != null) {
            if (selectedAlbum.listMusic == null) {
                selectedAlbum.listMusic = mutableListOf() // Khởi tạo listMusic nếu null
            }

            selectedAlbum.listMusic?.add(song!!)
            Log.d("ppp", "----${selectedAlbum.listMusic}")

            User.albumsLst[position] = selectedAlbum
            Log.d("ppp", "${User.albumsLst[position]}")

            dismiss()
        }

    }

}

