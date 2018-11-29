package bupin.com.tmpdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String   TAG = "MainActivity";
    private              TextView mTv;
    private              Button   mBt1;
    private              Button   mBt2;
    private              Button   mBt3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTv = findViewById(R.id.tv);
        mBt1 = findViewById(R.id.bt1);
        mBt2 = findViewById(R.id.bt2);
        mBt3 = findViewById(R.id.bt3);
        mBt1.setOnClickListener(this);
        mBt2.setOnClickListener(this);
        mBt3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String algorithm = "RSA";
        String input = "frank";
        String priPath = this.getFilesDir().getAbsolutePath() + File.separator + "a.pri";
        String pubPath = this.getFilesDir().getAbsolutePath() + File.separator + "a.pub";
        switch (v.getId()) {
            case R.id.bt1:
                Log.d(TAG, priPath);
                Log.d(TAG, pubPath);
                try {
                    RSADemo.generateKeys(algorithm, priPath, pubPath);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "生成key出异常了", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt2:
                try {
                    PrivateKey privateKey = RSADemo.getPrivateKey(priPath, algorithm);
                    PublicKey publicKey = RSADemo.getPublicKey(pubPath, algorithm);

                    String encryptData = RSADemo.RSAEncrypt(algorithm, privateKey, input, 245);
                    Log.d(TAG, "encryptData=" + encryptData);
                    String decryptData = RSADemo.RSADecrypt(algorithm, publicKey, encryptData, 256);
                    Log.d(TAG, "decryptData=" + decryptData);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "加解密出异常了", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt3:
                try {
//                    String eText = "XURae8f0eiLfhkrFVJ+2Kn1aZ4tIDs4Mo9vVd9tLo2WkpD6e/m2UhUB7KHnMhz0Q3enwpr+hTMEntDFY0ydu0Tk0cHtQeA1PjinhF4N/Z3D73167LDrULVmxvcZYrTA6rY7yj9noK8nqDGa2qNl/IF27+KTjBXxB6Wf1OUYR8Uvk04Yu9X4MU93T/KGRu3+N/ewX8mT6M7b8CFviGG5ADpwglTK6Tl2bt+lylbMDc1wtfnKxGcLCeIxKH9gazytoFP7scrc63lrD5gK3+lhb0nRPSk4v7fCd0+5ILwquQc5IkGuphjneyRSyNPKfpKaZcLKJw9B6YXtJGOQC1V3fFw==";
                    //后端用私钥加密过的数据
                    String eText = "ETLPedgx7vR9E9JGNIj4pLhvurcOM26oo4RgJhmeF5RvDXVdl3qQ+5H6hmUx3dV2K8jPxi5aKSVs1xnjuMgSfK32fjrqzYBULFaBCmnN1HbpcwYFNMA3enWiVwT3TAWFKA9ReJ2DWh0lkCzaHruOmcWCY3f2tjuEE9X9L0DN7m5R9iy2qgEEDPgfzImYYl8wltYdudryz2fQ7UNGdIUPc75EMqdvHEUrxIi5A7cM0BDGQI2aXD+39ijQCOVBtai/9ohF7YXtGmbsocPBKarhe8qpVIcvXza6fBbOWxBC6Z68uRGcljTVkhvNjWrEmRuu5pc3C41bx4OK9FD8kPgITg==";
                    //后端给的公钥
                    String pkey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmpz9g5IX3ElRtXFo2+9nwD4+amqhEH4Rz1FI2cXeSiQeLPfdCoSsflLovdJ21NxvcKGw9IvmjWkLESCVU/pxDeP2UkVXFAjC2KhZvoQO4v0x4Yn3/55bAAQ9O3qoGatjPlDbzr1CEAi+ZA7NY1Oz2TtOSq8Odc7wc3Sq6U1gZBf87w5jq0GwQwgLrQjaVf5oTgKmavyf6g8Uq8U0QnktXCJpJUsSSZdeWTwAhtKk+MDkd5VRHIynLklOgeAhjG7xzEAad/Q32qLGcCwY+ySiZWLZ5q5uZAys4rj98LiwV6zLyk8CYYclUDUtBPLLXDRN8DUEe4uKAucFC4IlkrXQ0wIDAQAB";
                    //获取公钥对象
                    KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
                    X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.decode(pkey, Base64.DEFAULT));
                    PublicKey publicKey = keyFactory.generatePublic(spec);
                    //指定解密algorithm = "RSA/ECB/PKCS1Padding"; Cipher cipher = Cipher.getInstance(algorithm);
                    algorithm = "RSA/ECB/PKCS1Padding";
                    String decrypt = RSADemo.RSADecrypt(algorithm, publicKey, eText, 256);
                    System.out.println("decript###" + decrypt + "###");
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "夸平台解密错误", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
