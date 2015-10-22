package course.example.pruebas_1.Adapters;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import course.example.pruebas_1.Interfaces.IAdaptersCaller;
import course.example.pruebas_1.Negocio.Transaccion;
import course.example.pruebas_1.R;
import course.example.pruebas_1.Util;

public class TransaccionesPagerAdapter1 extends Fragment implements IAdaptersCaller {

    /**
     * Key to insert the background color into the mapping of a Bundle.
     */
    private static final ArrayList<Transaccion> TRANSACCIONES = null;


    private ArrayList<Transaccion> transacciones;
    private TransaccionAdapter adapter;

    public static TransaccionesPagerAdapter1 newInstance(ArrayList<Transaccion> listaTransacciones) {

        // Instantiate a new fragment
        TransaccionesPagerAdapter1 fragment = new TransaccionesPagerAdapter1();
        // Save the parameters
        Bundle bundle = new Bundle();
        bundle.putSerializable("TRANSACCIONES", listaTransacciones);
        fragment.setArguments(bundle);
        fragment.setRetainInstance(true);

        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Load parameters when the initial creation of the fragment is done
        this.transacciones = (getArguments() != null) ? (ArrayList<Transaccion>)getArguments().getSerializable("TRANSACCIONES") : null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.transacciones_pager_adapter1, container, false);

        // Show the current page index in the view
        ListView lvTransaccionesPrincipal = (ListView) rootView.findViewById(R.id.lvTransaccionesPrincipal);
        this.adapter = new TransaccionAdapter(getActivity(),this.transacciones, false);
        adapter.setCallback(this);
        lvTransaccionesPrincipal.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return rootView;

    }

    @Override
    public void ActualizaVentana() {

    }
}
