package com.example.juanisaid.notitec;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Juan Isaid on 26/02/2018.
 */

public class RecyclerViewAdaptador1 extends RecyclerView.Adapter<RecyclerViewAdaptador1.ViewHolder>
{
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView departamento, descripcion, enlace, fecha, correo;
        CardView cv;

        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cvAlumno);
            departamento = (TextView) itemView.findViewById(R.id.lblDepartamento1);
            descripcion = (TextView) itemView.findViewById(R.id.lblDescripcion1);
            enlace = (TextView) itemView.findViewById(R.id.lblEnlace1);
            fecha = (TextView) itemView.findViewById(R.id.lblFecha1);
            correo = (TextView) itemView.findViewById(R.id.lblCorreo1);
            correo.setTextIsSelectable(true);
        }

    }

    public List<AlumnoModelo> alumnoLista;

    public RecyclerViewAdaptador1(List<AlumnoModelo> alumnoLista) {

        this.alumnoLista = alumnoLista;
    }

    @Override
    public RecyclerViewAdaptador1.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alumno, parent, false);
        RecyclerViewAdaptador1.ViewHolder viewHolder = new RecyclerViewAdaptador1.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdaptador1.ViewHolder holder, int position) {
        AlumnoModelo publicacion = alumnoLista.get(position);

        holder.departamento.setText(publicacion.getDepartamento());
        holder.descripcion.setText(publicacion.getDescripcion());
        holder.enlace.setText(publicacion.getEnlace());
        holder.fecha.setText(publicacion.getFecha());
        holder.correo.setText(publicacion.getCorreo());
    }

    @Override
    public int getItemCount() {
        return alumnoLista.size();
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
