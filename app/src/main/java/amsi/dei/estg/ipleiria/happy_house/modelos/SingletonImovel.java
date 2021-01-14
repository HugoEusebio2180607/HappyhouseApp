package amsi.dei.estg.ipleiria.happy_house.modelos;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.happy_house.R;
import amsi.dei.estg.ipleiria.happy_house.listeners.ImoveisListener;
import amsi.dei.estg.ipleiria.happy_house.utils.ImovelJsonParser;

public class SingletonImovel extends Application implements ImoveisListener {

    private static SingletonImovel instance = null;
    private ArrayList<Imovel> imovels;
    private ImovelBDHelper imovelsBD;

    private ImoveisListener imoveisListener;

    private static RequestQueue volleyQueue = null;
    //private String tokenAPI = "TOKEN";
    private String mUrlApiImoveis = "http://10.0.2.2:8888/imovel/";

    public static synchronized SingletonImovel getInstance(Context context){
        if (instance == null){
            instance = new SingletonImovel(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return  instance;
    }

    private  SingletonImovel(Context context){

        imovels = new ArrayList<>();
        imovelsBD = new ImovelBDHelper(context);
        //gerarImovel();
    }

    public ArrayList<Imovel> getImoveisBD(){
        return imovels = imovelsBD.getAllImoveisBD();
    }

    /*private void gerarImovel() {
        imovels = new ArrayList<>();
        imovels.add(new Imovel(1,231,3,2,2, R.drawable.casa,"leiria","Renovado","Bom estado","rua de leiria","3100-507",3243242,"Sim"));
        imovels.add(new Imovel(2,233,4,7,3,R.drawable.casa,"leiria","Usado","Otima","rua de coimbra","3100-504",33443242,"Sim"));
        imovels.add(new Imovel(3,241,3,2,2,R.drawable.casa,"leiria","Renovado,","Bom estado","rua de leiria","3100-507",3243242,"Sim"));
    }*/

    public ArrayList<Imovel> getImovels(){
        return  new ArrayList<>(imovels);
    }

    public Imovel getImovel(int id){
        for (Imovel i: imovels)
            if (i.getId()==id)
                return i;
        return null;
    }

    public void adicionarImovelBD(Imovel imovel){
        imovelsBD.adicionarImovelBD(imovel);
    }

    public void adicionarImoveisBD(ArrayList<Imovel> listaImoveis){
        imovelsBD.removerAllImoveisBD();
        for (Imovel imovel : listaImoveis){
            adicionarImovelBD(imovel);
        }
    }

    public void getAllImoveisAPI(final Context context, boolean isConnected){
        Toast.makeText(context, "ISCONNECTED : " + isConnected, Toast.LENGTH_SHORT).show();

        if (!isConnected){
            imovels = imovelsBD.getAllImoveisBD();
            if (imoveisListener != null){
                imoveisListener.onRefreshListaImoveis(imovels);
            }
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlApiImoveis, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    System.out.println(response);
                    imovels = ImovelJsonParser.parserJsonImoveis(response, context);
                    adicionarImoveisBD(imovels);
                    if (imoveisListener != null) {
                        imoveisListener.onRefreshListaImoveis(imovels);
                    }
                }
            }, new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("--> Erro: getAllImoveisAPI");
                }
            });
            volleyQueue.add(req);
        }
    }

    public void setImoveisListener(ImoveisListener imoveisListener){
        this.imoveisListener = imoveisListener;
    }

    @Override
    public void onRefreshListaImoveis(ArrayList<Imovel> listaImoveis) {

    }
}
