package com.example.ministerymob.Fav;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ministerymob.Market.Adresse;
import com.example.ministerymob.Market.RecyclerViewAdapter;
import com.example.ministerymob.Market.product;
import com.example.ministerymob.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class FavoriteFragment extends Fragment  implements DatePickerDialog.OnDateSetListener{
    private static HorizontalCalendar horizontalCalendar;
    private static RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private TextView vide;
    private Button affich_tt;

    private RecyclerView.Adapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root =inflater.inflate ( R.layout.favorite_fragment,container,false );

        affich_tt =root.findViewById ( R.id.afficher_tt );

        affich_tt.setBackgroundResource ( R.drawable.bouton_commencer );



        return root;
    }


    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        final TextView year =root.findViewById ( R.id.year );

        affich_tt.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                // afficher tout les enregistrements du user peu importe la date
                afficher_enreg ( null, root);


            }
        } );

        recyclerView = root.findViewById ( R.id.fav_recycleview );
        vide = root.findViewById ( R.id.vide );
        vide.setVisibility ( root.GONE );
        Date Year = Calendar.getInstance().getTime ();
        DateFormat dateFormat = new SimpleDateFormat ( "yyyy" );

        String strDate = dateFormat.format ( Year );

        year.setText (strDate );

        afficher_enreg (  Year,root);


        //  end after 1 month from now
        Calendar endDate = Calendar.getInstance();
        Calendar Date = Calendar.getInstance ();
        Date.add ( Calendar.DAY_OF_MONTH,-2 );
        endDate.add(Calendar.YEAR, 40);
        //  start before 1 month from now
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.YEAR, -10);
        horizontalCalendar = new HorizontalCalendar.Builder(root, R.id.calender)

                .datesNumberOnScreen(7)
                .range ( startDate,endDate )
                .defaultSelectedDate ( Date)

                .build();

        ImageView s=root.findViewById ( R.id.selectd );
        s .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DialogFragment datePicker = new DatePickerFragment ();
                datePicker.show(getActivity ().getSupportFragmentManager (), "date picker");



            }});


        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener () {
            @Override
            public void onDateSelected(Calendar date, int position) {
                //Date Year = horizontalCalendar.getDateAt ( position+1).getTime ();
                Date Year = horizontalCalendar.getSelectedDate ().getTime ();
                Calendar c= Calendar.getInstance ();


                DateFormat dateFormat = new SimpleDateFormat ( "yyyy" );
                String strDate = dateFormat.format ( Year );
                year.setText (strDate );
                Log.i("tttttttttttttt",strDate);
                Log.i("rrrrrrrrrrrrrrrr",Year.getDate ()+" "+position);

                afficher_enreg ( Year,root );
            }

            @Override
            public void onCalendarScroll(HorizontalCalendarView calendarView,
                                         int dx, int dy) {
            }

            @Override
            public boolean onDateLongClicked(Calendar date, int position) {
                Date Year = horizontalCalendar.getDateAt ( position ).getTime ();
                DateFormat dateFormat = new SimpleDateFormat ( "yyyy" );
                String strDate = dateFormat.format ( Year );
                year.setText (strDate );
                Log.i("tttttttttttttt",strDate);

                return true;
            }

        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);

        DateFormat date4= new SimpleDateFormat("EEEE", Locale.getDefault());
        String localTime = date4.format(calendar.getTime());

        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.YEAR, 40);
        Log.i("rrrrrrrrrrrrrrrr",dayOfMonth+" "+  localTime  );
        Calendar startDate = Calendar.getInstance();

        startDate.add(Calendar.YEAR, -10);
        horizontalCalendar.selectDate ( calendar,false );





    }


    public void afficher_enreg(Date date,   View view)
    {

        ArrayList<Enregistrement> list_enreg = new ArrayList<Enregistrement> ();
        ArrayList<product> MyProducts = new ArrayList<product> ();
        layoutManager = new LinearLayoutManager ( getActivity () );

        recyclerView.setLayoutManager ( layoutManager );
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration ( recyclerView.getContext (),
                layoutManager.getOrientation () );
        recyclerView.addItemDecoration ( dividerItemDecoration );


        if (date== null)
        {

            //todo get all the user's saves , putting them into list_enreg
        }

        else {

            DateFormat dateForma = new SimpleDateFormat ( "yyyy-MM-dd" );
            String strDat= dateForma.format ( date.getTime () );
            /******************************************
             * todo get enregesitrements which has date d'enregistrement = strDate, putting them into list_enreg
             */

        }

        Adresse a= new Adresse ("latitude","emptitude");

        list_enreg.add ( new Enregistrement ( new product ( "title","type","description","price","nom user","email user",0123,a,"chaise"  ),date));
        list_enreg.add ( new Enregistrement ( new product ( "title","type","description","price","nom user","email user",0123,a,"chaise"  ),date));
        list_enreg.add ( new Enregistrement ( new product ( "title","type","description","price","nom user","email user",0123,a,"chaise"  ),date));
        list_enreg.add ( new Enregistrement ( new product ( "title","type","description","price","nom user","email user",0123,a,"chaise"  ),date));
        list_enreg.add ( new Enregistrement ( new product ( "title","type","description","price","nom user","email user",0123,a,"chaise"  ),date));

        RecyclerView myrv = (RecyclerView)view. findViewById(R.id.fav_recycleview);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getActivity (),list_enreg);
        myrv.setLayoutManager(new GridLayoutManager (view.getContext (),2));
        myrv.setAdapter(myAdapter);

        recyclerView.setAdapter ( myAdapter );
    }
}
