package com.example.hp.sms;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final int PERMISSION_REQUEST_CODE = 909;
    private static final int PERMISSION_REQUEST_CODE2 = 24;
    private static final int PERMISSION_REQUEST_CODE3 = 34;
    Spinner country, state;
    String[] statesArray;
    ArrayAdapter statesAdapter;
    @BindView(R.id.Phone)
    EditText phone;
    int r;
    String rotp;
    SmsReadReciever smsReadReciever = new SmsReadReciever();
    String otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        country = (Spinner) findViewById(R.id.country);
        state = (Spinner) findViewById(R.id.State);

        ArrayAdapter<CharSequence> countryAdapter = ArrayAdapter.createFromResource(this, R.array.Country, R.layout.support_simple_spinner_dropdown_item);
        // countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country.setAdapter(countryAdapter);
        country.setOnItemSelectedListener(this);
    }

    @OnClick(R.id.register)
    public void click(View v)

    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Toast.makeText(this,"Android version",Toast.LENGTH_SHORT).show();
            if (checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                if (checkSelfPermission(Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED)
                    //Toast.makeText(this,"check permission",Toast.LENGTH_SHORT).show();
                    if (shouldShowRequestPermissionRationale(Manifest.permission.SEND_SMS)) {
                        //  Toast.makeText(this,"Repeate",Toast.LENGTH_SHORT).show();
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("SMS Permission")
                                .setMessage("Please give the SMS permission.")
                                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.M)
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        requestPermissions(new String[]{Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_CODE);
                                        requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS}, PERMISSION_REQUEST_CODE2);
                                    }
                                })
                                .setNegativeButton("No thanks", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(MainActivity.this, "Please grant the permission!", Toast.LENGTH_SHORT).show();
                                    }
                                }).show();
                    } else {
                        requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS}, PERMISSION_REQUEST_CODE2);
                        requestPermissions(new String[]{Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_CODE);
                        requestPermissions(new String[]{Manifest.permission.READ_SMS}, PERMISSION_REQUEST_CODE3);
                    }
            } else {
                sms();
                //  Toast.makeText(this,"else of check permission",Toast.LENGTH_SHORT).show();
            }
        } else {
            sms();
            //Toast.makeText(this,"run",Toast.LENGTH_SHORT).show();
        }
    }

    public void sms() {
        String phoneNo = phone.getText().toString();
        final int min = 111111;
        final int max = 999999;
        Random random = new Random();
        r= random.nextInt(((max - min) + 1) + min);
        Intent intent = new Intent();

        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNo, null, "" + r, pi, null);
        ConstraintLayout relativeLayout=(ConstraintLayout) findViewById(R.id.rlayout);
        ConstraintLayout verifyLayout=(ConstraintLayout) findViewById(R.id.vlayout);

        relativeLayout.setVisibility(View.INVISIBLE);
        verifyLayout.setVisibility(View.VISIBLE);
        // Intent i=new Intent(getApplicationContext(),verification.class);

        // startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //countryName= (String) parent.getItemAtPosition(position);
        //stateName=(String) parent.getItemAtPosition(position);
//        Toast.makeText(this, "You  clicked " + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
        if (parent.getItemAtPosition(position).equals("Afghanistan")) {
            // Toast.makeText(this, "States " + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            statesArray = new String[]{"Badakhshann", "Badghis", "Baghlan ", "Balkh ", "Bamyan ", "Farah ", "Faryab ", "Ghazni ", "Ghor ", "Hilmand ", "Hirat ", "Jawzjan ", "Kabul ", "Kandahar ", "Kapisa ", "Kunar ", "Kunduz ", "Laghman ", "Logar ", "Nangarhar ", "Nimroz ", "Paktika ", "Paktya ", "Parwan", "Samangan", "Takhar", "Uruzgan", "Wardak", "Zabul"};
            // statesAdapter=ArrayAdapter.createFromResource(this,getResources(R.array.Afghanisthan),R.layout.support_simple_spinner_dropdown_item);
            statesAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item, statesArray);
            state.setAdapter(statesAdapter);
        } else if (parent.getItemAtPosition(position).equals("Albania"))

        {
            statesArray = new String[]{"Berat", "Bulqize", "Delvine", "Devoli", "Diber", "Durres", "Elbasan", "Fier", "Gbirokaster", "Gransh", "Has", "Kavaje", "Kolonga", "korca", "kruje", "Kucova", "Kukes", "Lac", "Lezhe", "Librezhd", "Luxhmge", "Madhe", "" +
                    "Mallakaster", "Mad", "Mardite", "Peqin", "Permec", "Pogradec", "Puke", "Sarande", "Shkoder", "Skracar", "Tetelene", "Tirane", "Trotoje", "Vlore"};
            statesAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item, statesArray);
            state.setAdapter(statesAdapter);
        } else if (parent.getItemAtPosition(position).equals("Algeria")) {
            statesArray = new String[]{"48 Relizane", "Adrar", "Aïn Defla", "Aïn Témouchent", "Algiers", "Annaba", "Batna", "Béchar", "Béjaïa", "Biskra", "Blida", "Bordj Bou Arreridj", "Bouira", "Boumerdes", "Chlef", "Constantine", "Djlfa", "El Bayadh", "El Oued", "El Traf", "Ghardaia", "Guelma", "Illizi", "Jijel", "Khencela", "Laghouat", "Mascara", "Médéa", "Mila", "Mostaganem", "M'Sila", "Naama", "Oran",
                    "Ouarla", "Oum el-Bouaghi", "Saida", "Sétif", "Sidi Bel Abbs", "Skikda", "Souk Ahras", "Tamanghasset", "Tébessa", "Tiaret", "Tindouf", "Tipasa", "Tissemsilt", "Tizi Ouzou", "Tlemcen"};
            statesAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item, statesArray);
            state.setAdapter(statesAdapter);
        } else if (parent.getItemAtPosition(position).equals("Andorra")) {
            statesArray = new String[]{"Andorra la Vella", "Canillo", "Encamp", "Escaldes-Engordany", "La Massana", "Ordino", "Sant Julià de Lòria"};
            statesAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item, statesArray);
            state.setAdapter(statesAdapter);
        } else if (parent.getItemAtPosition(position).equals("Angola")) {
            statesArray = new String[]{"Bengo", "Benguela", "Bié", "Cabinda", "Cunene", "Huambo", "Huíla", "Kuando-Kubango", "Kwanza-Norte", "Kwanza-Sul", "Lunda-Norte", "Lunda-Sul", "Malanje", "Moxico", "Namibe", "Uíge", "Zaire"};
            statesAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item, statesArray);
            state.setAdapter(statesAdapter);
        } else if (parent.getItemAtPosition(position).equals("Anguilla")) {
            statesArray = new String[]{"Blowing Point", "Sandy Ground", "Sandy Hill", "The Valley", "	East End", "North Hill", "West End", "South Hill", "The Quarter", "North Side", "Island Harbour", "George Hill", "Stoney Ground", "The Farrington"};
            statesAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item, statesArray);
            state.setAdapter(statesAdapter);
        } else if (parent.getItemAtPosition(position).equals("Antigua Barbuda")) {
            statesArray = new String[]{"Saint George", "Saint John's", "Saint Mary", "Saint Paul", "Saint Peter", "Saint Philip"};
            statesAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item, statesArray);
            state.setAdapter(statesAdapter);
        } else if (parent.getItemAtPosition(position).equals("Argentina")) {
            statesArray = new String[]{" Buenos Aires", "Córdoba", "Santa Fe", "Autonomous City of Buenos Aires", " Mendoza", "Tucumán", " Salta", "Entre Ríos", "Misiones", " Chaco", "Corrientes", "Santiago del Estero", "Bandera de la Provincia de San Juan.svg San Juan", "Jujuy", "Río Negro", "Neuquén", "Formosa", "Chubut", "San Luis", "Catamarca", "La Rioja", "La Pampa", " Santa Cruz", " Tierra del Fuego"};
            statesAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item, statesArray);
            state.setAdapter(statesAdapter);
        } else if (parent.getItemAtPosition(position).equals("Armenia")) {
            statesArray = new String[]{"Yerevan", "Shirak", "Lorri", "Armavir", "Kotayk", "Ararat", "Geghark'unik", "Syunik", "Aragatsotn", "Tavush", "Vayots' Dzor"};
            statesAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item, statesArray);
            state.setAdapter(statesAdapter);
        } else if (parent.getItemAtPosition(position).equals("Australia")) {
            statesArray = new String[]{"New South Wales", "Queensland", "	Brisbane", " South Australia", " Tasmania", "Victoria", "Western Australia"};
            statesAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item, statesArray);
            state.setAdapter(statesAdapter);
        } else if (parent.getItemAtPosition(position).equals("Austria")) {
            statesArray = new String[]{"Vienna", "Lower Austria", "Upper Austria", "Styria", "Tyrol", "Carinthia", "Salzburg", "Vorarlberg", "Burgenland"};
            statesAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item, statesArray);
            state.setAdapter(statesAdapter);
        } else if (parent.getItemAtPosition(position).equals("Azerbaijan")) {
            statesArray = new String[]{"Beylagan Rayon", "Zangilan Rayon", "Yardymli Rayon", "Susa Rayonu", "Salyan Rayon", "Sabirabad Rayon", "Saatly Rayon", "Bilasuvar Rayon", "Neftchala Rayon", "Nakhchivan Autonomous Republic", "Masally District", "Lerik Rayon", "Lankaran Rayon", "Lacin Rayonu", "Qubadli Rayon", "Imishli Rayon", "Fizuli Rayon", "Jabrayil", "Jalilabad", "Astara District", "Xocali Rayonu", "Aghjabadi Rayon", "Aghdam Rayon", "Shirvan", "Lankaran Sahari", "Shusha", "Tartar Rayon", "Xankandi Sahari", "Khojavend District", "Zardab Rayon", "Zaqatala Rayon", "Yevlakh Rayon", "Oghuz Rayon", "Ujar Rayon", "Tovuz Rayon", "Shamakhi Rayon", "Shaki Rayon", "Shamkir Rayon", "Kurdamir Rayon", "Qabala Rayon", "Qusar Rayon", "Quba Rayon", "Goygol Rayon", "Khachmaz Rayon", "Kalbajar District", "Qazakh Rayon", "Goranboy Rayon", "Qakh Rayon", "Ismayilli Rayon", "Goychay Rayon", "Shabran Rayon", "Dashkasan Rayon", "Balakan Rayon", "Barda Rayon", "Baku City", "Absheron Rayon", "Aghsu Rayon", "Aghdash Rayon", "Gadabay Rayon", "Aghstafa Rayon", "Ganja City", "Mingacevir City", "Naftalan City", "Gobustan Rayon", "Samukh Rayon", "Shaki city", "Siazan Rayon", "Sumqayit City", "Khizi Rayon", "Yevlakh City", "Hajigabul Rayon", "Nakhchivan"};
            statesAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item, statesArray);
            state.setAdapter(statesAdapter);
        } else if (parent.getItemAtPosition(position).equals("Bahamas")) {
            statesArray = new String[]{};
            statesAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item, statesArray);
            state.setAdapter(statesAdapter);
        } else if (parent.getItemAtPosition(position).equals("Bahrain")) {
            statesArray = new String[]{"Al Manamah", "Sitrah", "Al Mintaqah al Gharbiyah", "Al Mintaqah al Wusta", "Al Mintaqah ash Shamaliyah", "Al Muharraq", "Al Asimah", "Ash Shamaliyah", "Jidd Hafs", "Madinat", "Madinat Hamad", "Mintaqat Juzur Hawar", "Ar Rifa", "Al Hadd"};
            statesAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item, statesArray);
            state.setAdapter(statesAdapter);
        } else if (parent.getItemAtPosition(position).equals("Bangladesh")) {
            statesArray = new String[]{"Mymensingh Division", "Rajshahi Division", "Dhaka Division", "Chittagong", "Khulna Division", "Barisal Division", "Sylhet Division", "Rangpur Division"};
            statesAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item, statesArray);
            state.setAdapter(statesAdapter);
        } else if (parent.getItemAtPosition(position).equals("Barbados")) {
            statesArray = new String[]{"Christ Church", "Saint Andrew", "Saint George", "Saint James", "Saint John", "Saint Joseph", "Saint Lucy", "Saint Michael", "Saint Peter", "Saint Philip", "Saint Thomas"};
            statesAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item, statesArray);
            state.setAdapter(statesAdapter);
        } else if (parent.getItemAtPosition(position).equals("Belarus")) {
            statesArray = new String[]{"City of Minsk", "Brest ", "Gomel", "Grodno", "Mogilev", "Minsk ", "Vitebsk"};
            statesAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item, statesArray);
            state.setAdapter(statesAdapter);
        } else if (parent.getItemAtPosition(position).equals("Belgium")) {
            statesArray = new String[]{"Antwerp", "East Flanders", "Flemish Brabant", "Limburg", "West Flanders", "Hainaut", " Liège", "Luxembourg", "Namur", "Walloon Brabant"};
            statesAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item, statesArray);
            state.setAdapter(statesAdapter);
        } else if (parent.getItemAtPosition(position).equals("India")) {
            statesArray = new String[]{"Andra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh", "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jammu and Kashmir", "Jharkhand", "Karnataka", "Kerala", "Madya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Orissa", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu", "Telagana", "Tripura\n", "Uttaranchal", "Uttar Pradesh", "West Bengal", "Andaman and Nicobar Islands", "Chandigarh", "Dadar and Nagar Haveli", "Daman and Diu\n", "Delhi", "Lakshadeep", "Pondicherry"};
            statesAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item, statesArray);
            state.setAdapter(statesAdapter);
        } else if (parent.getItemAtPosition(position).equals("Belize")) {
            statesArray = new String[]{"Belize", "Cayo", "Corozal", "Orange Walk", "Stann Creek", "Toledo"};
            statesAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item, statesArray);
            state.setAdapter(statesAdapter);
        } else if (parent.getItemAtPosition(position).equals("Benin")) {
            statesArray = new String[]{"Abomey", "Abomey-Calavi", "Athiémè", "Banikoara", "Bassila", "Bembèrèkè", "Bétérou", "Bohicon", "Bori", "Boukoumbé", "Comè", "Cotonou", "Cové", "Dassa-Zoumé", "Djougou", "Dogbo-Tota", "Ganvie", "Godomey", "Grand-Popo", "Kandi", "Kérou", "Kétou", "Kouandé", "Lokossa", "Malanville", "Natitingou", "Ndali", "Nikki", "Ouidah", "Parakou", "Péhonko", "Pobè", "Porga", "Sakété", "Savalou", "Savé", "Ségbana", "Tanguiéta", "Tchaourou"};
            statesAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item, statesArray);
            state.setAdapter(statesAdapter);
        } else if (parent.getItemAtPosition(position).equals("Bermuda")) {
            statesArray = new String[]{"Warwick Parish" + "Southampton Parish", "Smith's Parish", "Sandys Parish", "Saint George", "Pembroke Parish", "Paget Parish", "Hamilton", "Devonshire Parish"};
            statesAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item, statesArray);
            state.setAdapter(statesAdapter);
        } else if (parent.getItemAtPosition(position).equals("Bolivia")) {
            statesArray = new String[]{"Cercado", "San Javier", "Iténez", "Baures", "Huacaraje", "José Ballivián", "San Borja", "Santa Rosa", "Rurrenabaque", "Mamoré", "San Ramón", "Puerto Siles", "Marbán", "San Andrés", "Moxos", "Vaca Díez", "Guayaramerín", "Yacuma"};
            statesAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item, statesArray);
            state.setAdapter(statesAdapter);
        } else if (parent.getItemAtPosition(position).equals("Zambia")) {
            statesArray = new String[]{"Central", "Copperbelt", "Eastern", "Luapula", "Lusaka", "Muchinga", "North-Western", "Northern", "Southern", "Western"};
            statesAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item, statesArray);
            state.setAdapter(statesAdapter);
        } else if (parent.getItemAtPosition(position).equals("Zimbabwe")) {
            statesArray = new String[]{"Bulawayo", "Harare", "Manicaland", "Bindura", "Marondera", "Chinhoyi", "Masvingo", "Lupane", "Gwanda", "Midlands"};
            statesAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item, statesArray);
            state.setAdapter(statesAdapter);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(smsReadReciever, intentFilter);

        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                final String message = intent.getStringExtra("message");
//                your_edittext.setText(message);

                //Do whatever you want with the code here
EditText otpfilled=(EditText)findViewById(R.id.otp);
otpfilled.setText(message);
     otp=otpfilled.getText().toString();
rotp=String.valueOf(r);
                Toast.makeText(context, rotp, Toast.LENGTH_SHORT).show();
                if(otp.equals(rotp))
                {
                    Toast.makeText(context, "Sucessfully registered", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(context,"wrong OTP", Toast.LENGTH_SHORT).show();
                }            }

        }
    };
@OnClick(R.id.verify)
public void verify(View v){
    if(otp.equals(rotp))
    {
        Toast.makeText(this, "Sucessfully registered", Toast.LENGTH_SHORT).show();
    }
    else
    {
        Toast.makeText(this,"wrong OTP", Toast.LENGTH_SHORT).show();
    }
}
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(smsReadReciever);

        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
}
