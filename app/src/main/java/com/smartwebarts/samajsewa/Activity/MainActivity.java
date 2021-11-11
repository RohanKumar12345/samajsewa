package com.smartwebarts.samajsewa.Activity;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.smartwebarts.samajsewa.Adapter.MemberlistAdapter;
import com.smartwebarts.samajsewa.Helper.Contraints;
import com.smartwebarts.samajsewa.Helper.GetDataService;
import com.smartwebarts.samajsewa.Helper.PrefrenceManager;
import com.smartwebarts.samajsewa.Helper.RetrofitClintanse;
import com.smartwebarts.samajsewa.Model.ShowmemberModel;
import com.smartwebarts.samajsewa.R;
import com.smartwebarts.samajsewa.databinding.ActivityMainBinding;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;
    int pageHeight = 1120;
    int pagewidth = 792;
    Bitmap bitmap;
    Bitmap bmp, scaledbmp;
    private static final int PERMISSION_REQUEST_CODE = 200;
    ArrayList<ShowmemberModel> showmemberModels=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        userDetails();
        showMember();
        binding.homeinclude.opendrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.draweld.openDrawer(GravityCompat.START);
            }
        });


        binding.homeinclude.viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MemberAllActivity.class);
                startActivity(intent);

            }
        });

        Picasso.get().load(Contraints.photoUrl + new PrefrenceManager(getApplicationContext()).getPhoto()).into(binding.homeinclude.profileImg);
        Picasso.get().load(Contraints.photoUrl + new PrefrenceManager(getApplicationContext()).getPhoto()).into(binding.navBar.userImage);

        Log.e("profiles", new PrefrenceManager(getApplicationContext()).getPhoto());


        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        scaledbmp = Bitmap.createScaledBitmap(bmp, 250, 170, false);


        if (checkPermission()) {
            //  Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }


        binding.navBar.contcot.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
//
                binding.navBar.contcot.setBackgroundColor(getColor(R.color.light_blue));
                binding.navBar.profile.setBackgroundColor(View.GONE);
                binding.navBar.share.setBackgroundColor(View.GONE);
                binding.navBar.team.setBackgroundColor(View.GONE);
                binding.navBar.logout.setBackgroundColor(View.GONE);

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + "8707091593"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
//                binding.draweld.closeDrawers();
            }

        });


        binding.navBar.profile.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                binding.navBar.contcot.setBackgroundColor(View.GONE);
                binding.navBar.team.setBackgroundColor(View.GONE);
                binding.navBar.share.setBackgroundColor(View.GONE);
                binding.navBar.logout.setBackgroundColor(View.GONE);
                binding.navBar.profile.setBackgroundColor(getColor(R.color.light_blue));
//                binding.draweld.closeDrawers();

            }
        });
        binding.navBar.team.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyTeamActivity.class);
                startActivity(intent);
                binding.draweld.closeDrawers();
                binding.navBar.contcot.setBackgroundColor(View.GONE);
                binding.navBar.profile.setBackgroundColor(View.GONE);
                binding.navBar.share.setBackgroundColor(View.GONE);
                binding.navBar.logout.setBackgroundColor(View.GONE);
                binding.navBar.profile.setBackgroundColor(getColor(R.color.light_blue));
//                binding.draweld.closeDrawers();

            }
        });
        binding.navBar.share.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                binding.navBar.contcot.setBackgroundColor(View.GONE);
                binding.navBar.profile.setBackgroundColor(View.GONE);
                binding.navBar.team.setBackgroundColor(View.GONE);
                binding.navBar.logout.setBackgroundColor(View.GONE);
                binding.navBar.profile.setBackgroundColor(getColor(R.color.light_blue));
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Install the app Now : " + "https://play.google.com/store/apps/details?id=digi.coders.LDWClub");
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, "Share via");
                startActivity(shareIntent);
//                binding.draweld.closeDrawers();

            }
        });
        binding.navBar.logout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                binding.navBar.contcot.setBackgroundColor(View.GONE);
                binding.navBar.profile.setBackgroundColor(View.GONE);
                binding.navBar.share.setBackgroundColor(View.GONE);
                binding.navBar.team.setBackgroundColor(View.GONE);
                binding.navBar.profile.setBackgroundColor(getColor(R.color.light_blue));
                askforlogout();
//                binding.draweld.closeDrawers();

            }
        });
        binding.homeinclude.print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatePDF();
            }
        });




    }


    private void generatePDF() {
        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();
        Paint title = new Paint();
        Paint fathername = new Paint();
        Paint address = new Paint();
        Paint namess = new Paint();


        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create();
        PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);

        Canvas canvas = myPage.getCanvas();
        canvas.drawBitmap(scaledbmp, 56, 40, paint);
        title.setTextSize(30);
        title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        title.setColor(ContextCompat.getColor(this, R.color.black));
        canvas.drawText("Samaj Sewa", 350, 150, title);


        namess.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        namess.setColor(ContextCompat.getColor(this, R.color.black));
        namess.setTextSize(25);
        canvas.drawText("Name :- Rohan Kumar", 56, 400, namess);

        fathername.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        fathername.setColor(ContextCompat.getColor(this, R.color.black));
        fathername.setTextSize(25);
        canvas.drawText("Father's Name :- Rameshss Kumar", 56, 450, namess);

        address.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        address.setColor(ContextCompat.getColor(this, R.color.black));
        address.setTextSize(25);
        canvas.drawText("Village :- Maharupur, District :- Azamgarh\nUttar Pardesh 276406", 56, 500, namess);

        pdfDocument.finishPage(myPage);
        File files = new File(Environment.getExternalStorageDirectory(), "GFG.pdf");

        try {
            pdfDocument.writeTo(new FileOutputStream(files));
            Toast.makeText(MainActivity.this, "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pdfDocument.close();
    }

    private boolean checkPermission() {

        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {

                } else {
                    Toast.makeText(this, "Permission Denined.", Toast.LENGTH_SHORT).show();

                }
            }
        }
    }


    public void askforlogout() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure to exit");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        new PrefrenceManager(MainActivity.this).logout();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void userDetails() {

        final android.app.AlertDialog loadingBar = new SpotsDialog(MainActivity.this, R.style.Custom);
        loadingBar.setCancelable(false);
        loadingBar.show();
        GetDataService getDataService = RetrofitClintanse.getRetrofit().create(GetDataService.class);
        Call<JsonObject> call = getDataService.user_details(new PrefrenceManager(getApplicationContext()).getUserid());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                loadingBar.dismiss();
                Log.e("userDetails_response", response.body().toString());
                try {
                    JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                    String status = jsonObject.getString("status");
                    if (status.equalsIgnoreCase("1")) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("User_data");
                        binding.homeinclude.codNo.setText("Candidate No :- " + jsonObject1.getString("member_id"));
                        binding.homeinclude.refralNo.setText("My Refral :- " + jsonObject1.getString("my_refral_code"));
                        binding.homeinclude.name.setText(jsonObject1.getString("name"));
                        binding.navBar.name.setText(jsonObject1.getString("name"));
                        binding.homeinclude.fathername.setText(jsonObject1.getString("fname"));
                        binding.homeinclude.village.setText(jsonObject1.getString("address"));
                        binding.homeinclude.post.setText(jsonObject1.getString("city") + "-" + jsonObject1.getString("pincode"));
                        binding.homeinclude.email.setText(jsonObject1.getString("email"));
                        binding.navBar.email.setText(jsonObject1.getString("email"));
                        binding.homeinclude.mobile.setText(jsonObject1.getString("perma_contact_no"));
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("userDetails_exp", e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("userDetails_error", t.getMessage());
                loadingBar.dismiss();
            }
        });
    }


    public void showMember() {
        final android.app.AlertDialog loadingBar = new SpotsDialog(MainActivity.this, R.style.Custom);
        loadingBar.setCancelable(false);
        loadingBar.show();
        GetDataService getDataService = RetrofitClintanse.getRetrofit().create(GetDataService.class);
        Call<JsonArray> call = getDataService.showsMembers("operator");
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                loadingBar.dismiss();
                Log.e("showmemb_response", response.body().toString());

                try {
                    JSONArray jsonArray = new JSONArray(new Gson().toJson(response.body()));
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            ShowmemberModel showmemberModel=new Gson().fromJson( jsonArray.getJSONObject(i).toString(),ShowmemberModel.class );
                            showmemberModels.add( showmemberModel );

                        }

                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        binding.homeinclude.memberRecylviw.setLayoutManager(layoutManager);
                        binding.homeinclude.memberRecylviw.setHasFixedSize(true);
                        MemberlistAdapter adapter = new MemberlistAdapter(getApplicationContext(),showmemberModels);
                        binding.homeinclude.memberRecylviw.setAdapter(adapter);

                    if (showmemberModels.size() < 9) {
                        binding.homeinclude.viewall.setVisibility( View.GONE );
                    } else {
                        binding.homeinclude.viewall.setVisibility( View.VISIBLE );
                    }


                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, e.getMessage() + "", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.e("showmemb_error", t.getMessage());
                loadingBar.dismiss();

            }
        });


    }
}

