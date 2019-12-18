package com.infy.telstraassignment_1.mvp.canadaslist;

import android.os.Build;
import com.google.gson.Gson;
import com.infy.telstraassignment_1.model.Canada;
import com.infy.telstraassignment_1.model.CanadaModel;
import com.infy.telstraassignment_1.model.CanadaRepo;
import com.infy.telstraassignment_1.mvp.canadaslist.CanadasListContract;
import com.infy.telstraassignment_1.network.APIUtils;
import com.infy.telstraassignment_1.model.CanadaRepo;
import com.infy.telstraassignment_1.roomdb.RoomEntity;
import java.util.ArrayList;
import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CanadaPresenter implements CanadaRepo {

    CanadasListContract contract;

    public CanadaPresenter(CanadasListContract contract) {
        this.contract = contract;
    }


    @Override
    public void list(List<RoomEntity> roomEntityList){
        ArrayList<Canada> canadasModelArrayList = new ArrayList<>();
        for (int i = 0; i<roomEntityList.size(); i++){
            Canada canada = new Canada();
            canada.setTitle(roomEntityList.get(i).getTitle());
            canada.setDescription(roomEntityList.get(i).getDescription());
            canada.setImageHref(roomEntityList.get(i).getImageUrl());
            canadasModelArrayList.add(canada);
        }
        contract.setList(canadasModelArrayList);
    }

    @Override
    public void prepareLocalDbList(ArrayList<Canada> titlesModelArrayList){
        ArrayList<RoomEntity> titlesArrayList = new ArrayList<>();
        for (int i=0; i<titlesModelArrayList.size(); i++){
            RoomEntity canadas = new RoomEntity(titlesModelArrayList.get(i).getTitle(),
                    titlesModelArrayList.get(i).getDescription(),titlesModelArrayList.get(i).getImageHref());
            titlesArrayList.add(canadas);
        }
        try {
            contract.clearLocalDb(titlesArrayList);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public void getCanadasList(){
        if (contract.checkIntentConnection()) {
            Call<ResponseBody> call = APIUtils.getAPI("s/2iodh4vg0eortkl/facts.json");
            contract.showLoading();
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        assert response.body() != null;
                        CanadaModel canadasModel = new Gson().fromJson(response.body().string(), CanadaModel.class);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            contract.setActionBarTitle(canadasModel.getTitle());
                        } else {
                            contract.setActionBarTitle("TelstraAssignment");
                        }
                        if (canadasModel.getRows().size() != 0) {
                            contract.setList(canadasModel.getRows());
                            prepareLocalDbList(canadasModel.getRows());
                        }
                        contract.hideRefreshing();
                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            contract.hideRefreshing();
                            contract.showToast(e.getMessage());
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    try {
                        contract.hideRefreshing();
                        contract.showToast(t.getMessage());
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            });
        } else {
            try {
                contract.showToast("Please check your Internet Connection");
                contract.hideRefreshing();
                contract.getCanadaListFromLocal();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }



}
