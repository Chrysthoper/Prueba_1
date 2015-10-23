package course.example.pruebas_1.Adapters;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import course.example.pruebas_1.Interfaces.IAdaptersCallerGrid;
import course.example.pruebas_1.Interfaces.IAdaptersCallerGridCategoriasGroup;
import course.example.pruebas_1.Interfaces.IAdaptersCallerVentada;
import course.example.pruebas_1.Negocio.Categoria;
import course.example.pruebas_1.Negocio.Transaccion;
import course.example.pruebas_1.R;

public class TransaccionesPagerAdapter2 extends Fragment implements IAdaptersCallerGridCategoriasGroup {

    /**
     * Key to insert the background color into the mapping of a Bundle.
     */
    private static final ArrayList<Categoria> CATEGORIAS = null;


    private ArrayList<Categoria> categorias;
    private CategoriasGroupAdapter adapter;
    ListView lvTransaccionesPrincipal;

    public static TransaccionesPagerAdapter2 newInstance(ArrayList<Categoria> listaCategorias) {

        // Instantiate a new fragment
        TransaccionesPagerAdapter2 fragment = new TransaccionesPagerAdapter2();
        // Save the parameters
        Bundle bundle = new Bundle();
        bundle.putSerializable("CATEGORIAS", listaCategorias);
        fragment.setArguments(bundle);
        fragment.setRetainInstance(true);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Load parameters when the initial creation of the fragment is done
        this.categorias = (getArguments() != null) ? (ArrayList<Categoria>)getArguments().getSerializable("CATEGORIAS") : null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.transacciones_pager_adapter1, container, false);

        // Show the current page index in the view
        lvTransaccionesPrincipal = (ListView) rootView.findViewById(R.id.lvTransaccionesPrincipal);
        this.adapter = new CategoriasGroupAdapter(getActivity(),this.categorias);
        adapter.setCallback(this);
        lvTransaccionesPrincipal.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return rootView;

    }

    @Override
    public void ActualizaGrid(ArrayList<Categoria> listaCategorias) {
        this.categorias = listaCategorias;
        this.adapter = new CategoriasGroupAdapter(getActivity(),this.categorias);
        lvTransaccionesPrincipal.setAdapter(adapter);
        adapter.setCallback(this);
        adapter.notifyDataSetChanged();
    }
}
