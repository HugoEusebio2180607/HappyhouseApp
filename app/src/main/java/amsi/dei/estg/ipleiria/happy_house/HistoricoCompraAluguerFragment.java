package amsi.dei.estg.ipleiria.happy_house;

import android.content.Intent;
import android.os.Bundle;

import amsi.dei.estg.ipleiria.happy_house.adaptadores.ListaImovelAdaptador;
import amsi.dei.estg.ipleiria.happy_house.modelos.Imovel;
import amsi.dei.estg.ipleiria.happy_house.modelos.SingletonImovel;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class HistoricoCompraAluguerFragment extends Fragment {

    private ListView lvListaImoveis;
    private ArrayList<Imovel> listaImoveis;

    public HistoricoCompraAluguerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_historico_compra_aluguer, container, false);
        lvListaImoveis = view.findViewById(R.id.lvListaHistoricoCompraAluguer);
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