package joss.polinema.ahmadrizaldi.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import joss.polinema.ahmadrizaldi.Adapter.Adapter_Choose_Room;
import joss.polinema.ahmadrizaldi.Model.RoomHotel_Fix;
import joss.polinema.ahmadrizaldi.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoomFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<RoomHotel_Fix> roomHotelArrayList = new ArrayList<>();
    private Adapter_Choose_Room mAdapter;

    DatabaseReference Room;

    public RoomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_room, container, false);

        Room = FirebaseDatabase.getInstance().getReference("Room Hotel");

        //Inisialisasi recycleview
        recyclerView = (RecyclerView)view.findViewById(R.id.daftarbooking_user2);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        //menset adapter dengan context dan arraylist
        mAdapter = new Adapter_Choose_Room(getContext(), roomHotelArrayList);
        //menset adapter dari recycleview
        recyclerView.setAdapter(mAdapter);

        //melakukan loaddata pada realtimedatabase
        Room.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                //memasukkan value yang didapat kedalam class roomhotel
                RoomHotel_Fix Room = dataSnapshot.getValue(RoomHotel_Fix.class);

                String r1 = "",r2 = "",r3= "",r4 = "",r5 ="",r6 = "";

                //cek gambar jika null
                if (Room.getGm1()!= null){
                    r1 = Room.getGm1();
                }

                //cek gambar jika null
                if (Room.getGm2()!= null){
                    r2 = Room.getGm2();
                }

                //cek gambar jika null
                if (Room.getGm3()!= null){
                    r3 = Room.getGm3();
                }

                //cek gambar jika null
                if (Room.getGm4()!= null){
                    r4 = Room.getGm4();
                }

                //cek gambar jika null
                if (Room.getGm5()!= null){
                    r5 = Room.getGm5();
                }

                //cek gambar jika null
                if (Room.getGm6()!= null){
                    r6 = Room.getGm6();
                }


                //Melakukan penambahan data kedalam arraylist
                    tambahData(Room.getIDHotel(), Room.getID_Room(), Room.getNamaRoom(), Room.getHarga(), r1, r2, r3, r4, r5, r6,
                            Room.getUkuranKamar(), Room.getTipeTempatTidur(), Room.getFasilitasKamar(), Room.getFasilitasKamarMandi(), Room.getDeskripsiKamar(), Room.getStok());

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;
    }

    private void tambahData(String IDHotel, String ID_Room, String namaRoom, String harga, String gm1, String gm2, String gm3,
                            String gm4, String gm5, String gm6, String ukuranKamar, String tipeTempatTidur, String fasilitasKamar,
                            String fasilitasKamarMandi, String deskripsiKamar, String stok){
        //inisialisasi class
        RoomHotel_Fix rm = new RoomHotel_Fix(IDHotel, ID_Room, namaRoom, harga, gm1, gm2, gm3,gm4,gm5,gm6, ukuranKamar, tipeTempatTidur, fasilitasKamar,
                fasilitasKamarMandi,deskripsiKamar, stok);
        //menambah class kedalam arraylist
        roomHotelArrayList.add(rm);

        //memberitahu ke adapter jika terdapat perubahan data
        mAdapter.notifyDataSetChanged();
    }

}
