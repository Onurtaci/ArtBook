package com.onurtaci.artbook.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import com.onurtaci.artbook.adapter.ArtAdapter;
import com.onurtaci.artbook.databinding.FragmentFirstBinding;
import com.onurtaci.artbook.model.Art;
import com.onurtaci.artbook.roomdb.ArtDao;
import com.onurtaci.artbook.roomdb.ArtDatabase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FirstFragment extends Fragment {
    ArrayList<Art> artList;
    ArtAdapter artAdaptor;
    ArtDatabase db;
    ArtDao artDao;
    private FragmentFirstBinding binding;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public FirstFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private void handleResponse(List<Art> artList) {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        ArtAdapter placeAdaptor = new ArtAdapter((ArrayList<Art>) artList, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
        binding.recyclerView.setAdapter(placeAdaptor);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        db = Room.databaseBuilder(requireContext(), ArtDatabase.class, "Arts").build();
        artDao = db.artDao();

        artList = new ArrayList<>();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        artAdaptor = new ArtAdapter(artList, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openSecondFragment(artList.get(position));
            }
        });
        binding.recyclerView.setAdapter(artAdaptor);

        compositeDisposable.add(artDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(FirstFragment.this::handleResponse)
        );

        return view;
    }

    private void openSecondFragment(Art selectedArt) {
        /*
        SecondFragment secondFragment = new SecondFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("selected_art", selectedArt);
        secondFragment.setArguments(bundle);
        */

        // İkinci fragment'ı açın
        NavDirections action = (NavDirections) FirstFragmentDirections.actionFirstFragmentToSecondFragment(1, selectedArt);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        compositeDisposable.clear();
    }
}