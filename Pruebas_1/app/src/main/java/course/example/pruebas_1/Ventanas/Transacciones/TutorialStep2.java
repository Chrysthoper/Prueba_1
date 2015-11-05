package course.example.pruebas_1.Ventanas.Transacciones;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.persistence.ContextVariable;

import java.util.ArrayList;

import course.example.pruebas_1.Adapters.CategoriaAdapter;
import course.example.pruebas_1.Adapters.CuentasGridAdapter;
import course.example.pruebas_1.DB.DBHelper;
import course.example.pruebas_1.Negocio.Categoria;
import course.example.pruebas_1.Negocio.Cuenta;
import course.example.pruebas_1.Negocio.Transaccion;
import course.example.pruebas_1.R;
import course.example.pruebas_1.Ventanas.Categorias.abcCategorias;

public class TutorialStep2 extends WizardStep{

    @ContextVariable
    private Transaccion trans;
    ArrayList<Categoria> ListaCategorias;
    ArrayList<Cuenta> ListaCuenta;
    GridView lvCategoriasPaso2;
    private DBHelper dbHelper;
    CategoriaAdapter adapter1;
    CuentasGridAdapter adapter2;

    //You must have an empty constructor for every step
    public TutorialStep2() {

    }

    //Set your layout here
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.ventana_transaccion_paso2, container, false);
        dbHelper = new DBHelper(getActivity().getApplicationContext());

        lvCategoriasPaso2 = (GridView)v.findViewById(R.id.lvCategoriasPaso2);

        if(trans.tipo_transaccion < 2)
        {
            ListaCategorias = dbHelper.Categorias.ObtenPorTipo(trans.tipo_transaccion);
            adapter1 = new CategoriaAdapter(v.getContext(), ListaCategorias, trans.numeroCategoria);
            lvCategoriasPaso2.setAdapter(adapter1);
            lvCategoriasPaso2.setOnItemClickListener(click_grid);
        }
        else
        {
            ListaCuenta = dbHelper.Cuentas.Obten();
            adapter2 = new CuentasGridAdapter(v.getContext(), ListaCuenta, trans.cuenta_secu_id);
            lvCategoriasPaso2.setAdapter(adapter2);
            lvCategoriasPaso2.setOnItemClickListener(click_grid);
        }
        return v;
    }

    AdapterView.OnItemClickListener click_grid = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position,
        long id) {

            if(trans.tipo_transaccion < 2)
            {
                if(position > 0) {
                    int idCategoria = ListaCategorias.get(position).id;
                    if (trans.numeroCategoria != idCategoria) {
                        if (trans.numeroCategoria != -1 && trans.categoriaObj != null)
                            lvCategoriasPaso2.getChildAt(ListaCategorias.indexOf(trans.categoriaObj)).setBackground(null);
                        trans.numeroCategoria = ListaCategorias.get(position).id;
                        trans.categoriaObj = ListaCategorias.get(position);
                        v.setBackgroundColor(Color.parseColor("#FFAA2300"));
                    } else {
                        lvCategoriasPaso2.getChildAt(ListaCategorias.indexOf(trans.categoriaObj)).setBackground(null);
                        trans.numeroCategoria = -1;
                    }
                }
                else {
                    startActivityForResult(new Intent(v.getContext(), abcCategorias.class), 1);
                }
            }
            else
            {
                int idCuenta = ListaCuenta.get(position).id;
                if (trans.cuenta_secu_id != idCuenta)
                {
                    if (trans.cuenta_secu_id != -1 && trans.cuentaSecundariaObj != null)
                        lvCategoriasPaso2.getChildAt(ListaCuenta.indexOf(trans.cuentaSecundariaObj)).setBackground(null);
                    trans.cuenta_secu_id = ListaCuenta.get(position).id;
                    trans.cuentaSecundariaObj = ListaCuenta.get(position);
                    v.setBackgroundColor(Color.parseColor("#FFAA2300"));
                }
                else
                {
                    lvCategoriasPaso2.getChildAt(ListaCuenta.indexOf(trans.cuentaSecundariaObj)).setBackground(null);
                    trans.cuenta_secu_id = -1;
                }
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1)
        {
            if(resultCode != 1)
                Toast.makeText(getActivity().getApplicationContext(), "No se genero ninguna categoria", Toast.LENGTH_SHORT).show();
            else
            {
                if(trans.tipo_transaccion < 2)
                {
                    ListaCategorias = dbHelper.Categorias.ObtenPorTipo(trans.tipo_transaccion);
                    adapter1 = new CategoriaAdapter(getActivity().getApplicationContext(),ListaCategorias, -1);
                    lvCategoriasPaso2.setAdapter(adapter1);
                    adapter1.notifyDataSetChanged();
                }
                else
                {
                    ListaCuenta = dbHelper.Cuentas.Obten();
                    adapter2 = new CuentasGridAdapter(getActivity().getApplicationContext(), ListaCuenta, trans.cuenta_secu_id);
                    lvCategoriasPaso2.setAdapter(adapter2);
                    adapter2.notifyDataSetChanged();
                }
            }
        }
    }
}