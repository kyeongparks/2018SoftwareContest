package com.example.kyeon.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NewActivity extends AppCompatActivity {
    String travel_title;
    DrawerLayout drawerLayout;
    LinearLayout navigation_new_background;
    Intent intent;

    private Button btnPlaceName;
    private String currentDay;
    private String placeName;
    private String placeLat;
    private String placeLng;
    private String placeType;
    private String placeBitmapFilePath;

    private ListView navigationView;
    private String[] navItems = {"메인 메뉴", "새 여행", "내 여행", "다른 여행"/*,"추천 여행"*/};

    public static final String TAG = "Alert_Dialog";
    private boolean isFirstPlaceSet = false;
    AlertDialog.Builder alertDialogCalendar;
    AlertDialog.Builder personEmpty;
    AlertDialog.Builder departingEmpty;
    AlertDialog.Builder arrivingEmpty;
    AlertDialog.Builder titleEmpty;
    AlertDialog.Builder placeEmpty;

    final int REQUEST_CODE_CALENDAR = 100;
    final int REQUEST_CODE_CHOOSE_PLACE = 0x11;
    int AorD = 0;
    int yy = 0, mm = 0, dd = 0; // yy : defines year | mm : defines month | dd : defines day
    String today_yy, today_mm, today_dd;
    String date, year, month, day;
    String d_yy, d_mm, d_dd;
    String a_yy, a_mm, a_dd;
    int personCount = 0; // variable for spinner selection, for each selection, personCount gets selected item's data

    int check_dyy = 0, check_dmm = 0, check_ddd = 0;
    int check_ayy = 0, check_amm = 0, check_add = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        Toolbar newToolbar;
        drawerLayout = (DrawerLayout)findViewById(R.id.newActivity_drawer);
        navigation_new_background = (LinearLayout)findViewById(R.id.navigation_new_background);

        final EditText eTitle = (EditText) findViewById(R.id.travelTitle);
        Spinner spinner = (Spinner) findViewById(R.id.countPerson);
        final ArrayAdapter sAdapter = ArrayAdapter.createFromResource(this, R.array.question, android.R.layout.simple_spinner_dropdown_item);
        Button departButton, arrivingButton;

        btnPlaceName = (Button)findViewById(R.id.btnPlaceName);
        btnPlaceName.setOnClickListener(new Button.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(isFirstPlaceSet) {
                   AlertDialog.Builder alertDialog = new AlertDialog.Builder(NewActivity.this);
                   alertDialog.setTitle(getResources().getString(R.string.replace_place_title))
                           .setMessage(getResources().getString(R.string.replace_place_description))
                           .setCancelable(true)
                           .setPositiveButton(getResources().getString(R.string.dialog_ok),
                                   new DialogInterface.OnClickListener() {
                                       @Override
                                       public void onClick(DialogInterface dialogInterface, int i) {
                                           Intent intent = new Intent(NewActivity.this, ChooseFirstPlaceActivity.class);
                                           startActivityForResult(intent, REQUEST_CODE_CHOOSE_PLACE);
                                       }
                                   })
                           .setNegativeButton(getResources().getString(R.string.dialog_no),
                                   new DialogInterface.OnClickListener() {
                                       @Override
                                       public void onClick(DialogInterface dialogInterface, int i) {
                                           dialogInterface.cancel();
                                       }
                                   });

                   AlertDialog dialog = alertDialog.create();
                   dialog.show();
               } else {
                   Intent intent = new Intent(NewActivity.this, ChooseFirstPlaceActivity.class);
                   startActivityForResult(intent, REQUEST_CODE_CHOOSE_PLACE);
               }
           }
        });

        departButton = (Button)findViewById(R.id.departingDate);
        arrivingButton = (Button)findViewById(R.id.arrivingDate);

        long todayTime = System.currentTimeMillis();
        Date todayDate = new Date(todayTime);
        SimpleDateFormat todayDateFormat = new SimpleDateFormat("/yyyy/MM/dd", Locale.KOREA);
        String CurrentDate = todayDateFormat.format(todayDate);

        today_yy = CurrentDate.substring(1, 5);
        today_mm = CurrentDate.substring(6, 8);
        today_dd = CurrentDate.substring(9, 11);

        d_yy = today_yy;
        d_mm = today_mm;
        d_dd = today_dd;

        a_yy = today_yy;
        a_mm = today_mm;
        a_dd = today_dd;

        departButton.setText(today_yy+"/"+today_mm+"/"+today_dd);
        arrivingButton.setText(today_yy+"/"+today_mm+"/"+today_dd);


        newToolbar = (Toolbar)findViewById(R.id.newActivitytoolbar);
        newToolbar.setTitle("새 여행");
        setSupportActionBar(newToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.outline_list_black_18dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        navigationView = (ListView)findViewById(R.id.navigation_contents_from_new);
        navigationView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, navItems));
        navigationView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent;
                switch (i) {
                    case 0:
                        finish();
                        break;

                    case 1:
                        break;

                    case 2:
                        /*intent = new Intent(
                                getApplicationContext(),
                                MyTripFragment.class);
                        startActivity(intent);
                        finish();
                        break;*/
                        finish();
                        break;
                    case 3:
                        finish();
                        break;
                        /*intent = new Intent(
                                getApplicationContext(),
                                OthersFragment.class);
                        startActivity(intent);
                        finish();
                        break;*/
                    /*case 4:
                        intent = new Intent(
                                getApplicationContext(),
                                RecommendTravel.class);
                        startActivity(intent);
                        finish();
                        break;*/
                }
                drawerLayout.closeDrawer(navigation_new_background);
            }
        });

        spinner.setAdapter(sAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(NewActivity.this, (CharSequence) sAdapter.getItem(i), Toast.LENGTH_SHORT).show();
                /*********************************
                 Spinner index starts from 0.
                 index[0] = 선택;
                 index[1] = 2명;
                 index[2] = 3명;
                 index[3] = 4명;
                 index[4] = 단체;
                 *********************************/

                if(i == 0) {
                    personCount = 1;
                }

                if (i == 1) {
                    personCount = 2;
                }
                if (i == 2) {
                    personCount = 3;
                }
                if (i == 3) {
                    personCount = 4;
                }
                if (i == 4) {
                    personCount = 99; // group consist of more than 4 members is defined as group. (integer number 99 means group)
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //place_text = ePlace.getText().toString();

        Button btn_comp = (Button) findViewById(R.id.btn_comp);
        btn_comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                travel_title = eTitle.getText().toString();

                if(isFirstPlaceSet == false || travel_title == null) {
                    Log.d("DEBUG-TEST", "isFirstPlaceSet : " + isFirstPlaceSet +", travel_title : " + travel_title);
                    showWarningDialog();
                } else {
                    Intent i = new Intent(NewActivity.this, TripPlanActivity.class);

                    if (travel_title.length() == 0)
                        travel_title = "여행을 떠나요~!!";

                    Log.d("DEBUG-TEST", "전달되는 여행 타이틀 : " + travel_title);

                    Log.d("DEBUG-TEST", d_dd+a_dd);

                    if(d_mm.length() == 1) d_mm = "0" + d_mm;
                    if(a_mm.length() == 1) a_mm = "0" + a_mm;
                    if(d_dd.length() == 1) d_dd = "0" + d_dd;
                    if(a_dd.length() == 1) a_dd = "0" + a_dd;

                    String d_date = d_yy+""+d_mm+""+d_dd;
                    String a_date = a_yy+""+a_mm+""+a_dd;
                    int d_date_int = Integer.parseInt(d_date);
                    int a_date_int = Integer.parseInt(a_date);

                    if(a_date_int < d_date_int) {
                        Log.d("DEBUG-TEST", "Departing Date cannot be later than Arriving Date : "+d_date_int+" > "+a_date_int);
                        alertDialogCalendar = new AlertDialog.Builder(NewActivity.this);
                        alertDialogCalendar.setTitle("일정 선택 오류입니다!!");
                        alertDialogCalendar.setMessage("* 출발일정은 도착일정보다 늦을 수 없습니다.\n* 도착일정은 출발일정보다 빠를 수 없습니다.");
                        alertDialogCalendar.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                Log.v(TAG, "확인");
                                dialog.dismiss();
                            }
                        });
                        alertDialogCalendar.show();
                    }

                    else {
                        i.putExtra("departing_year", d_yy);
                        i.putExtra("departing_month", d_mm);
                        i.putExtra("departing_day", d_dd);
                        i.putExtra("arriving_year", a_yy);
                        i.putExtra("arriving_month", a_mm);
                        i.putExtra("arriving_day", a_dd);
                        i.putExtra("person_count", String.valueOf(personCount));
                        i.putExtra(MapUtility.TRAVEL_TITLE_TAG, travel_title);
                        i.putExtra("place_name", travel_title);
                        i.putExtra(MapUtility.PLACE_NAME_TAG, btnPlaceName.getText());
                        i.putExtra(MapUtility.PLACE_LAT_TAG, placeLat);
                        i.putExtra(MapUtility.PLACE_LNG_TAG, placeLng);
                        i.putExtra(MapUtility.PLACE_TYPE_TAG, placeType);
                        i.putExtra(MapUtility.CURRENT_DAY_TAG, currentDay);
                        if(placeBitmapFilePath == null) {
                            Log.d("DEBUG-TEST", getResources().getString(R.string.intent_bitmap_error) + "in NewActivity");
                        }
                        i.putExtra(MapUtility.PLACE_BITMAP_FILE_PATH_TAG, placeBitmapFilePath);
                        startActivity(i);
                        finish();
                    }
                }
            }
        });
    }

    private void showWarningDialog() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(getResources().getString(R.string.warning_title))
                .setMessage(getResources().getString(R.string.warning_description))
                .setCancelable(true)
                .setPositiveButton(getResources().getString(R.string.dialog_ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }

    public void onclickCalendarA(View v) {
        Intent intent = new Intent(this, Calendar.class);
        startActivityForResult(intent, REQUEST_CODE_CALENDAR);
        AorD = 1;
    }

    public void onclickCalendarB(View v) {
        Intent intent = new Intent(this, Calendar.class);
        startActivityForResult(intent, REQUEST_CODE_CALENDAR);
        AorD = 2;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK)
            return;

        if (requestCode == REQUEST_CODE_CALENDAR) {
            date = data.getStringExtra("date");
            year = data.getStringExtra("year");
            month = data.getStringExtra("month");
            day = data.getStringExtra("day");

            yy = Integer.parseInt(year);
            mm = Integer.parseInt(month);
            dd = Integer.parseInt(day);

            if (AorD == 1) {
                d_yy = year;
                d_mm = month;
                d_dd = day;

                check_dyy = Integer.parseInt(d_yy);
                check_dmm = Integer.parseInt(d_mm);
                check_ddd = Integer.parseInt(d_dd);

                check_ayy = 0;
                check_amm = 0;
                check_add = 0;

                if(check_ayy != 0 && check_amm != 0 && check_add != 0) {
                    if (check_ayy < check_dyy || check_amm < check_dmm || check_add < check_ddd) {
                        alertDialogCalendar = new AlertDialog.Builder(NewActivity.this);
                        alertDialogCalendar.setTitle("일정 선택 오류입니다!!");
                        alertDialogCalendar.setMessage("출발일정은 도착일정보다 늦을 수 없습니다.");
                        alertDialogCalendar.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                Log.v(TAG, "확인");
                                dialog.dismiss();
                            }
                        });
                        alertDialogCalendar.show();
                    } else {
                        Button button = (Button) findViewById(R.id.departingDate);
                        button.setText(date);
                    }
                }
                else {
                    Button button = (Button) findViewById(R.id.departingDate);
                    button.setText(date);

                    //Button Abutton = (Button) findViewById(R.id.arrivingDate);
                    //Abutton.setText(date);

                }

            }

            if (AorD == 2) {
                a_yy = year;
                a_mm = month;
                a_dd = day;

                check_ayy = Integer.parseInt(a_yy);
                check_amm = Integer.parseInt(a_mm);
                check_add = Integer.parseInt(a_dd);

                check_dyy = 0;
                check_dmm = 0;
                check_ddd = 0;

                System.out.println(check_add);
                System.out.println(check_ddd);


                if(check_dyy != 0 && check_dmm != 0 && check_ddd != 0) {
                    if ((check_ayy < check_dyy) || (check_amm < check_dmm) || (check_add < check_ddd)) {
                        System.out.println(1);
                        alertDialogCalendar = new AlertDialog.Builder(NewActivity.this);
                        alertDialogCalendar.setTitle("일정 선택 오류입니다!!");
                        alertDialogCalendar.setMessage("도착일정은 출발 일정 보다 빠를 수 없습니다.");
                        alertDialogCalendar.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                Log.v(TAG, "확인");
                                dialog.dismiss();
                            }
                        });
                        alertDialogCalendar.show();
                    } else {
                        Button button = (Button) findViewById(R.id.arrivingDate);
                        button.setText(date);
                    }
                }
                else {
                    Button button = (Button) findViewById(R.id.arrivingDate);
                    button.setText(date);
                }
            }
        } else if(requestCode == REQUEST_CODE_CHOOSE_PLACE) {
            placeName = data.getStringExtra(MapUtility.PLACE_NAME_TAG);
            if(placeName == null)
                placeName = getResources().getString(R.string.default_place_name);
            btnPlaceName.setText(placeName);
            // placeLatLng = data.getStringExtra
            isFirstPlaceSet = true;
            placeLat = data.getStringExtra(MapUtility.PLACE_LAT_TAG);
            placeLng = data.getStringExtra(MapUtility.PLACE_LNG_TAG);
            placeType = data.getStringExtra(MapUtility.PLACE_TYPE_TAG);
            currentDay = data.getStringExtra(MapUtility.CURRENT_DAY_TAG);
            placeBitmapFilePath = data.getStringExtra(MapUtility.PLACE_BITMAP_FILE_PATH_TAG);
            if(placeBitmapFilePath == null)
                Log.d(".java", getResources().getString(R.string.intent_bitmap_error) + "in NewActivity");
        }

    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

