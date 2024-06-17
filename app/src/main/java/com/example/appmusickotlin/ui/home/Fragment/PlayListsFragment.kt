package com.example.appmusickotlin.ui.home.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appmusickotlin.ui.adapter.AlbumAdapter
import com.example.appmusickotlin.ui.adapter.MusicAdapter
import com.example.appmusickotlin.databinding.FragmentPlaylistsfragmentBinding
import com.example.appmusickotlin.model.DataListPlayList
import com.example.appmusickotlin.model.Song
import com.example.appmusickotlin.model.User
import com.example.appmusickotlin.ui.popup.DialogAddPlaylistFragment
import com.example.appmusickotlin.util.callBack.OnEditButtonClickListener
import com.example.appmusickotlin.util.callBack.OnItemClickListener
import com.example.appmusickotlin.util.callBack.PlaylistAddedListener
import com.example.appmusickotlin.viewmodel.PlaylistViewModel


class PlayListsFragment : Fragment(), PlaylistAddedListener, OnItemClickListener,
    OnEditButtonClickListener {
    private lateinit var binding: FragmentPlaylistsfragmentBinding
    private var position : Int = 0
    private lateinit var playlistViewModel : PlaylistViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlaylistsfragmentBinding.inflate(inflater, container, false)
        playlistViewModel = ViewModelProvider(requireActivity()).get(PlaylistViewModel::class.java)

        playlistViewModel.getPlaylist(User.userId!!)
        playlistViewModel.playlist.observe(requireActivity(), Observer { playlist ->

            val album = User.albumsLst
            binding.grSwap.visibility = View.GONE
            binding.ibnSwap.visibility = View.GONE
            binding.ibnLinear.visibility = View.GONE
            binding.ibnGrid.visibility = View.GONE

            if(playlist.isNullOrEmpty()){
                binding.group.visibility = View.VISIBLE
                binding.rccAlbum.visibility = View.GONE
                binding.rccMusicAlbum.visibility = View.GONE

            }
            else {
                val adapter = AlbumAdapter(playlist,this)
                binding.rccAlbum.layoutManager = LinearLayoutManager(this.context)
                binding.rccAlbum.adapter = adapter
                binding.group.visibility = View.GONE
                binding.rccAlbum.visibility = View.VISIBLE
                binding.rccMusicAlbum.visibility = View.GONE
            }

        })

        //hien lai man hinh album khi back lai
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,object:
            OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                binding.group.visibility = View.GONE
                binding.rccAlbum.visibility = View.VISIBLE
                binding.rccMusicAlbum.visibility = View.GONE
                binding.grSwap.visibility = View.GONE
                binding.ibnSwap.visibility = View.GONE
                binding.ibnLinear.visibility = View.GONE
                binding.ibnGrid.visibility = View.GONE
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddPlaylist.setOnClickListener {
            val dialogAddPlaylist = DialogAddPlaylistFragment()
            dialogAddPlaylist.setPlaylistAddedListener(this) // phải thêm hàm này vào
            dialogAddPlaylist.show(childFragmentManager, "Add Playlist")
        }


        binding.ibnGrid.setOnClickListener{
            binding.ibnGrid.visibility = View.GONE
            binding.ibnLinear.visibility = View.VISIBLE

            val selectedAlbum = User.albumsLst.get(position)
            val musicList = selectedAlbum.listMusic

            if(musicList.isNullOrEmpty()){
                Toast.makeText(context, "chua co music ", Toast.LENGTH_SHORT).show()
            } else {
                binding.rccAlbum.visibility = View.GONE
                binding.rccMusicAlbum.visibility = View.GONE
                binding.rccGridMusicAlbum.visibility = View.VISIBLE
                binding.grSwap.visibility = View.GONE
                binding.ibnSwap.visibility = View.VISIBLE
                binding.ibnGrid.visibility = View.GONE
                binding.ibnLinear.visibility = View.VISIBLE
            }

        }


        binding.ibnLinear.setOnClickListener{
            binding.ibnLinear.visibility = View.GONE
            binding.ibnGrid.visibility = View.VISIBLE
            val selectedAlbum = User.albumsLst.get(position)
            val musicList = selectedAlbum.listMusic

            if(musicList.isNullOrEmpty()){
                Toast.makeText(context, "chua co music ", Toast.LENGTH_SHORT).show()
            } else {
                binding.rccAlbum.visibility = View.GONE
                binding.rccGridMusicAlbum.visibility = View.GONE
                binding.rccMusicAlbum.visibility = View.VISIBLE
                binding.grSwap.visibility = View.GONE
                binding.ibnSwap.visibility = View.VISIBLE
                binding.ibnGrid.visibility = View.VISIBLE
                binding.ibnLinear.visibility = View.GONE

                //musicAdapter.enableSwipeAndDrag(binding.rccMusicAlbum)
            }
        }
    }
    override fun onPlaylistAdded(album: DataListPlayList) {

        playlistViewModel.getPlaylist(User.userId!!)
        playlistViewModel.playlist.observe(requireActivity(), Observer { playlist ->
//            User.albumsLst = listAlbum
            Log.e("User","///${playlist}")
            val adapter = AlbumAdapter(playlist,this)
            binding.rccAlbum.layoutManager = LinearLayoutManager(this.context)
            binding.rccAlbum.adapter = adapter
            binding.group.visibility = View.GONE
            binding.rccAlbum.visibility = View.VISIBLE
        })
//        val listAlbum = User.albumsLst
//        listAlbum.add(album)





    }

    override fun onItemClick(position: Int) {

        val selectedAlbum = User.albumsLst.get(position)
        val musicList = selectedAlbum.listMusic

        if(musicList.isNullOrEmpty()){
            Toast.makeText(context, "chua co music ", Toast.LENGTH_SHORT).show()
        } else {
            val musicAdapter = MusicAdapter(this.context, musicList,this,false,false)
            binding.rccMusicAlbum.layoutManager = LinearLayoutManager(this.context)
            binding.rccMusicAlbum.adapter = musicAdapter

            val musicGridAdapter = MusicAdapter(this.context, musicList,this,false,true)
            binding.rccGridMusicAlbum.layoutManager = GridLayoutManager(this.context,2)
            binding.rccGridMusicAlbum.adapter = musicGridAdapter

            binding.group.visibility = View.GONE
            binding.rccAlbum.visibility = View.GONE
            binding.rccMusicAlbum.visibility = View.VISIBLE
            binding.rccGridMusicAlbum.visibility = View.GONE
            binding.grSwap.visibility = View.GONE
            binding.ibnSwap.visibility = View.VISIBLE
            binding.ibnGrid.visibility = View.VISIBLE

            binding.ibnSwap.setOnClickListener {
                binding.grSwap.visibility = View.VISIBLE
                binding.ibnSwap.visibility = View.GONE
                musicAdapter.setSwap(swap = true)
                musicGridAdapter.enableSwipeAndDrag(binding.rccGridMusicAlbum,true)
                musicAdapter.enableSwipeAndDrag(binding.rccMusicAlbum,true)
            }
            binding.grSwap.setOnClickListener {
                binding.grSwap.visibility = View.GONE
                binding.ibnSwap.visibility = View.VISIBLE
                musicAdapter.setSwap(swap = false)
                musicGridAdapter.enableSwipeAndDrag(binding.rccGridMusicAlbum,false)
                musicAdapter.enableSwipeAndDrag(binding.rccMusicAlbum,false)
            }
//            binding.ibnOk.setOnClickListener {
//                binding.grSwap.visibility = View.GONE
//                binding.ibnSwap.visibility = View.VISIBLE
//                musicAdapter.setSwap(swap = false)
//                musicGridAdapter.enableSwipeAndDrag(binding.rccGridMusicAlbum)
//                musicAdapter.enableSwipeAndDrag(binding.rccMusicAlbum)
//            }




        }

        getPosition(position)
    }

    private fun getPosition(position: Int): Int {
        this.position = position
        return  position
    }

    override fun onEditButtonClick(song: Song) {
    }

    override fun onDeleteButtonClick(song: Song, position: Int) {
        // code chat GPT
        val selectedAlbum = User.albumsLst.find { it.listMusic?.contains(song) == true }
        selectedAlbum?.listMusic?.remove(song)
        binding.rccMusicAlbum.adapter?.notifyItemRemoved(position)
    }


}