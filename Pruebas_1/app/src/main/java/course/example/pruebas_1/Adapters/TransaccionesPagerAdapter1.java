package course.example.pruebas_1.Adapters;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import course.example.pruebas_1.Interfaces.IAdaptersCallerGrid;
import course.example.pruebas_1.Interfaces.IAdaptersCallerVentana;
import course.example.pruebas_1.Negocio.Transaccion;
import course.example.pruebas_1.R;

public class TransaccionesPagerAdapter1 extends Fragment implements IAdaptersCallerGrid {

    private IAdaptersCallerVentana caller;
    /**
     * Key to insert the background color into the mapping of a Bundle.
     */
    private static final ArrayList<Transaccion> TRANSACCIONES = null;

    private ListView lvTransaccionesPrincipal;
    private ArrayList<Transaccion> transacciones;
    private boolean ConFechas;
    private TransaccionAdapter adapter;

    public static TransaccionesPagerAdapter1 newInstance(ArrayList<Transaccion> listaTransacciones, boolean ConFechas) {

        // Instantiate a new fragment
        TransaccionesPagerAdapter1 fragment = new TransaccionesPagerAdapter1();
        // Save the parameters
        Bundle bundle = new Bundle();
        bundle.putSerializable("TRANSACCIONES", listaTransacciones);
        bundle.putBoolean("CONFECHAS", ConFechas);
        fragment.setArguments(bundle);
        fragment.setRetainInstance(true);

        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Load parameters when the initial creation of the fragment is done
        this.transacciones = (getArguments() != null) ? (ArrayList<Transaccion>)getArguments().getSerializable("TRANSACCIONES") : null;
        this.ConFechas = (getArguments() != null) ? (boolean)getArguments().getBoolean("CONFECHAS") : false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.transacciones_pager_adapter1, container, false);

        // Show the current page index in the view
        lvTransaccionesPrincipal = (ListView) rootView.findViewById(R.id.lvTransaccionesPrincipal);
        this.adapter = new TransaccionAdapter(getActivity(),this.transacciones, ConFechas);
        adapter.setCallback(this);
        lvTransaccionesPrincipal.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return rootView;
    }

    @Override
    public void ActualizaGrid(ArrayList<Transaccion> listaTransacciones) {
        this.transacciones = listaTransacciones;
        this.adapter = new TransaccionAdapter(getActivity(),this.transacciones, ConFechas);
        lvTransaccionesPrincipal.setAdapter(adapter);
        adapter.setCallback(this);
        adapter.notifyDataSetChanged();
        caller.ActualizaVentana();
    }

    public void setCallback(IAdaptersCallerVentana caller){
        this.caller = caller;
    }
}
