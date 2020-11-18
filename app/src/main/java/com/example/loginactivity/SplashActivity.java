//package com.example.loginactivity;
//
//import androidx.appcompat.app.AppCompatActivity;
//import android.app.ActivityOptions;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.util.Pair;
//import android.view.View;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.os.Bundle;
//import com.example.xircls.R;
//
//public class SplashActivity extends AppCompatActivity {
//
//
//    private static int SPLASH_SCREEN=1500;
//    Animation topAnim,bottomAnim;
//    ImageView imageView;
//    TextView textView;
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);
//        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
//        bottomAnim=AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
//        imageView=findViewById(R.id.imageView);
//        //  textView=findViewById(R.id.welcometxt);
//
//        imageView.setAnimation(topAnim);
//        //  textView.setAnimation(bottomAnim);
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent=new Intent(SplashScreen.this,LoginUser.class);
//                Pair[] pair=new Pair[1];
//                pair[0]=new Pair<View,String>(imageView,"logo_image");
//                // pair[1]=new Pair<View,String>(textView,"logo_text");
//
//                ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this,pair);
//                startActivity(intent,options.toBundle());
//
//            }
//        },SPLASH_SCREEN);
//
//    }
//}
//    }
//}