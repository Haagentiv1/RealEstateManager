package com.example.realestatemanager.ui.propertyList

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.realestatemanager.R
import com.example.realestatemanager.databinding.PropertyListFragmentBinding
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PropertyListFragment : Fragment() {
    private var _binding: PropertyListFragmentBinding? = null
    private val binding get() = _binding!!


    private val viewModel by viewModels<PropertyListViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PropertyListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView: RecyclerView = binding.propertyListFragmentRv
        val adapter = PropertyListAdapter {
            viewModel.onPropertyClicked(it)
        }

        recyclerView.adapter = adapter
        viewModel.propertyLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        val toolbar = requireActivity().findViewById<MaterialToolbar>(R.id.topAppBar)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.top_app_bar_menu,menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
               return true
            }
        }, viewLifecycleOwner,Lifecycle.State.RESUMED)

        toolbar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.menu_item_filter -> {
                    Log.e("list","click")
                    if (binding.fragmentListRlContainer.visibility == View.GONE){
                        binding.fragmentListRlContainer.visibility = View.VISIBLE
                    }else {
                        binding.fragmentListRlContainer.visibility = View.GONE
                    }
                    true

                }else ->{
                Log.e("list","esle")
                true
            }
            }

        }

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}