package com.sistemadegestaoagricola.reuniao;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.sistemadegestaoagricola.R;
import com.sistemadegestaoagricola.adapter.AtaFotosReuniaoAdapter;
import com.sistemadegestaoagricola.entidades.Reuniao;

public class AtaReuniaoFragment extends Fragment {

    public AtaReuniaoFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bitmap[] lista = Reuniao.getAtasBitmap().toArray(new Bitmap[Reuniao.getAtasBitmap().size()]);
        View view = inflater.inflate(R.layout.fragment_ata_reuniao, container, false);

        GridView gridView = view.findViewById(R.id.gvAtaReuniaoFragment);
        gridView.setAdapter(new AtaFotosReuniaoAdapter(getActivity(),lista));
        return view;
    }
}