package com.example.juanisaid.notitec;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan Isaid on 26/02/2018.
 */

public class RecyclerViewAdaptador  extends RecyclerView.Adapter<RecyclerViewAdaptador.ViewHolder>
{
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView departamento,descripcion,enlace,fecha,correo;
        CardView cv;
        //ImageView foto;

        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cvPublicacion);
            departamento = (TextView)itemView.findViewById(R.id.lblDepartamento);
            descripcion = (TextView)itemView.findViewById(R.id.lblDescripcion);
            enlace = (TextView)itemView.findViewById(R.id.lblEnlace);
            fecha = (TextView)itemView.findViewById(R.id.lblFecha);
            correo = (TextView)itemView.findViewById(R.id.lblCorreo);
            //foto = (ImageView)itemView.findViewById(R.id.img);
        }
    }
    //            String
    public List<PublicacionModelo> publicacionLista;
    public ArrayList<PublicacionModelo> publicacionLista2;
/*
    public RecyclerViewAdaptador(List<String> publicacionLista2)
    {
        this.publicacionLista2=publicacionLista2;
    }*/

    //                                  String
    public RecyclerViewAdaptador(List<PublicacionModelo> publicacionLista)
    {
        this.publicacionLista= publicacionLista;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_publicacion,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.departamento.setText(publicacionLista.get(position).getDepartamento());
        holder.descripcion.setText(publicacionLista.get(position).getDescripcion());
        holder.enlace.setText(publicacionLista.get(position).getEnlace());
        holder.fecha.setText(publicacionLista.get(position).getFecha());
        holder.correo.setText(publicacionLista.get(position).getCorreo());
        //holder.foto.setImageResource(publicacionLista.get(position).getFoto());
    }

    @Override
    public int getItemCount() {
        return publicacionLista.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
