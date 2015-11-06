package course.example.pruebas_1.Adapters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import course.example.pruebas_1.Interfaces.IAdaptersCallerGrid;
import course.example.pruebas_1.Interfaces.IAdaptersCallerVentana;
import course.example.pruebas_1.Negocio.Transaccion;
import course.example.pruebas_1.R;
import course.example.pruebas_1.Ventanas.Transacciones.TutorialActivity;

public class TransaccionesPagerAdapter1 extends Fragment implements IAdaptersCallerGrid {

    private IAdaptersCallerVentana caller;

    public ListView lvTransaccionesPrincipal;
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

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.adapter_transacciones_pager1, container, false);

        // Show the current page index in the view
        lvTransaccionesPrincipal = (ListView) rootView.findViewById(R.id.lvTransaccionesPrincipal);
        //this.adapter = new TransaccionAdapter(getActivity(),this.transacciones);
        //adapter.setCallback(this);
        //lvTransaccionesPrincipal.setAdapter(adapter);
        //adapter.notifyDataSetChanged();
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            this.adapter = new TransaccionAdapter(getActivity(),this.transacciones);
            adapter.setCallback(this);
            lvTransaccionesPrincipal.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        lvTransaccionesPrincipal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Transaccion trans = transacciones.get(position);
                final Intent intent = new Intent(getActivity(), TutorialActivity.class);
                intent.putExtra("FECHA", trans.fecha_alta);
                intent.putExtra("TIPO", trans.tipo_transaccion);
                intent.putExtra("OP", 'C');
                intent.putExtra("TRANS", trans);
                intent.putExtra("CUENTA_ID", trans.cuenta_prin_id);//Cuentas.get(which).id);
                getActivity().startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public void ActualizaGrid(ArrayList<Transaccion> listaTransacciones) {
        this.transacciones = listaTransacciones;
        this.adapter = new TransaccionAdapter(getActivity(),this.transacciones);
        lvTransaccionesPrincipal.setAdapter(adapter);
        adapter.setCallback(this);
        adapter.notifyDataSetChanged();
        caller.ActualizaVentana();
    }

    public void setCallback(IAdaptersCallerVentana caller){
        this.caller = caller;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

}
