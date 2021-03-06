package amsi.dei.estg.ipleiria.happy_house;

import amsi.dei.estg.ipleiria.happy_house.modelos.Imovel;
import amsi.dei.estg.ipleiria.happy_house.modelos.SingletonImovel;
import amsi.dei.estg.ipleiria.happy_house.modelos.User;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DetalhesImovelActivity extends AppCompatActivity /*implements OnMapReadyCallback*/ {

    public static final String ID = "ID";
    private Imovel imovel;
    private TextView tvEstado, tvArea, tvWCs, tvQuartos, tvGaragem, tvPiso, tvDescricao, tvCidade, tvMorada, tvCodigoPostal, tvPreco;
    private ImageView imgCapa;
    private Button btnMapa;
    private MenuItem itemFavorito;
    private Boolean isFavourite = false;
    private ArrayList<String> favoritos;

    public static final String CHAVE_ID = "ID";
    public static final String SECCAO_INFO_USER = "SECCAO_INFO_USER";
    public static final String CHAVE_FAVORITOS = "FAVORITOS";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_imovel);

//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync((OnMapReadyCallback) this);

        sharedPreferences = this.getSharedPreferences(SECCAO_INFO_USER, Context.MODE_PRIVATE);
        int idUser = sharedPreferences.getInt(CHAVE_ID, -1);

        int id = getIntent().getIntExtra(ID, -1);
        imovel = SingletonImovel.getInstance(getApplicationContext()).getImovel(id);

        user = SingletonImovel.getInstance(getApplicationContext()).getUser(idUser);

        if (user.getFavoritos() == null){
            favoritos = new ArrayList<>();
        } else {
            favoritos = new ArrayList<>(Arrays.asList(user.getFavoritos().split(", ")));
            if (favoritos.contains(String.valueOf(id))){
                isFavourite = true;
            }
        }




        tvDescricao = findViewById(R.id.tvDescricao);
        tvEstado = findViewById(R.id.tvEstadoInfo);
        tvArea = findViewById(R.id.tvAreaInfo);
        tvWCs = findViewById(R.id.tvWcInfo);
        tvQuartos = findViewById(R.id.tvQuartosInfo);
        tvGaragem = findViewById(R.id.tvGaragemInfo);
        tvPiso = findViewById(R.id.tvPisoInfo);
        tvCidade = findViewById(R.id.tvCidadeInfo);
        tvMorada = findViewById(R.id.tvMoradaInfo);
        tvCodigoPostal = findViewById(R.id.tvCodigoPostalInfo);
        tvPreco = findViewById(R.id.tvPrecoInfo);
        imgCapa = findViewById(R.id.imageView2);

        btnMapa = findViewById(R.id.btnMap);
        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra(MapsActivity.LATITUDE, imovel.getLatitude());
                intent.putExtra(MapsActivity.LONGITUDE, imovel.getLongitude());
                startActivity(intent);
            }
        });

        setTitle("Detalhes: ");

        tvDescricao.setText(imovel.getDescricao());
        tvEstado.setText(imovel.getEstado());
        tvArea.setText(imovel.getArea()+"");
        tvWCs.setText(imovel.getNwc()+"");
        tvQuartos.setText(imovel.getNquartos()+"");
        tvGaragem.setText(imovel.getGaragem()+"");
        tvPiso.setText(imovel.getPiso()+"");
        tvCidade.setText(imovel.getCidade());
        tvMorada.setText(imovel.getMorada());
        tvCodigoPostal.setText(imovel.getCodigo_postal());
        tvPreco.setText(imovel.getPreco()+"");
        //imgCapa.setImageResource(imovel.getImagem());
        imgCapa.setImageResource(R.drawable.casa);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favorito, menu);

        itemFavorito = menu.findItem(R.id.itemFavorito);
        if (isFavourite){
            itemFavorito.setIcon(R.drawable.ic_star);
        }

        return(super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemFavorito:
                if (!isFavourite){
                    item.setIcon(R.drawable.ic_star);
                    isFavourite = true;
                    favoritos.add(String.valueOf(imovel.getId()));
                    SingletonImovel.getInstance(getApplicationContext()).editarUserFavoritosAPI( editarUserFavoritos() ,getApplicationContext());
                }  else {
                    item.setIcon(R.drawable.ic_star_border);
                    isFavourite = false;
                    favoritos.remove(String.valueOf(imovel.getId()));
                    SingletonImovel.getInstance(getApplicationContext()).editarUserFavoritosAPI( editarUserFavoritos() ,getApplicationContext());
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private User editarUserFavoritos(){

        //String listFav = String.join(", ", favoritos);
        String listFav = TextUtils.join(", ", favoritos);
        user.setFavoritos(listFav);

        sharedPreferences = getApplicationContext().getSharedPreferences(SECCAO_INFO_USER, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(CHAVE_FAVORITOS, listFav);
        editor.apply();

        return user;
    }

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        // Add a marker in Sydney, Australia,
//        // and move the map's camera to the same location.
//        LatLng sydney = new LatLng(-33.852, 151.211);
//        googleMap.addMarker(new MarkerOptions().position(sydney)
//                .title("Marker in Sydney"));
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//    }
}