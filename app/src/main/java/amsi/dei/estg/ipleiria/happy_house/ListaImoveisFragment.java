package amsi.dei.estg.ipleiria.happy_house;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.happy_house.R;
import amsi.dei.estg.ipleiria.happy_house.adaptadores.ListaImovelAdaptador;
import amsi.dei.estg.ipleiria.happy_house.modelos.Imovel;
import amsi.dei.estg.ipleiria.happy_house.modelos.SingletonImovel;


public class ListaImoveisFragment extends Fragment {

    private ListView lvListaImoveis;
    private ArrayList<Imovel>listaImoveis;

    public ListaImoveisFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_imoveis, container, false);
        lvListaImoveis = view.findViewById(R.id.lvListaImoveis);
        listaImoveis= SingletonImovel.getInstance(getContext()).getImovels();
        lvListaImoveis.setAdapter(new ListaImovelAdaptador(getContext(),listaImoveis));

        lvListaImoveis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                //Imovel tempImovel = (Imovel) adapterView.getItemAtPosition(i);
                //Toast.makeText(getContext(), "AQUI: " + tempImovel.getId(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getContext(), DetalhesImovelActivity.class);
                intent.putExtra(DetalhesImovelActivity.ID, (int) id);
                startActivity(intent);
            }
        });

        return view;
    }
}