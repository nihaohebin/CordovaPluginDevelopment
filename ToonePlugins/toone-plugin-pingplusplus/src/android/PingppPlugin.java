package cordova.plugin.pingpp;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pingplusplus.android.PaymentActivity;
import com.pingplusplus.android.Pingpp;
import com.pingplusplus.android.PingppLog;
import com.pingplusplus.ui.ChannelListener;
import com.pingplusplus.ui.PaymentHandler;
import com.pingplusplus.ui.PingppUI;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class PingppPlugin extends CordovaPlugin {

    private String TAG = "PingppPlugin";
    private CallbackContext callbackContext;

    @Override
    public boolean execute(String action, JSONArray data, final CallbackContext callbackContext) throws JSONException {
        this.callbackContext = callbackContext;
        Log.i(TAG, "action = " + action + "\n data = " + data);
        String charge;
        switch (action) {
            case "createPayment": {
                charge = data.get(0).toString();

                Intent intent = new Intent(this.cordova.getActivity(), PaymentActivity.class);
                intent.putExtra(PaymentActivity.EXTRA_CHARGE, charge);
                this.cordova.startActivityForResult(this, intent, Pingpp.REQUEST_CODE_PAYMENT);
                return true;
            }
            case "getVersion":
                callbackContext.success(Pingpp.VERSION);
                return true;
            case "setDebugMode":
                String enable = data.get(0).toString();
                PingppLog.DEBUG = "true".equals(enable);
                break;
            case "showPaymentChannels":
                String channels = data.get(0).toString();
                channels = channels.replaceAll("\"", "");
                PingppUI.enableChannels(channels.substring(1, channels.length() - 1).split(","));
                PingppUI.showPaymentChannels(this.cordova.getActivity(), new ChannelListener() {
                    @Override
                    public void selectChannel(String s) {
                        callbackContext.success(s);
                    }
                });
                return true;
            case "createPay":
                charge = data.get(0).toString();
                PingppUI.createPay(this.cordova.getActivity(), charge, new PaymentHandler() {
                    @Override
                    public void handlePaymentResult(Intent data) {
                        int code = data.getExtras().getInt("code");
                        String result = data.getExtras().getString("result");
                        try {
                            JSONObject object = new JSONObject();
                            if (code == 0) {
                                object.put("result", "cancel");
                                object.put("error", result);
                            } else if (code == -1) {
                                object.put("result", "fail");
                                object.put("error", result);
                            } else if (code == 1) {
                                object.put("result", "success");
                            }
                            callbackContext.success(object);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                return true;
            case "pay": {

                JSONObject jsonObject = data.getJSONObject(0);
                String type = jsonObject.has("channel") ? jsonObject.getString("channel") : "";
                String amountText = jsonObject.has("amount") ? jsonObject.getString("amount") : "";
                String subject = jsonObject.has("subject") ? jsonObject.getString("subject") : "";
                String body = jsonObject.has("body") ? jsonObject.getString("body") : "";
                String chargeServerURL = jsonObject.has("getChargeServerURL") ? jsonObject.getString("getChargeServerURL") : "";
                boolean liveMode = jsonObject.has("liveMode") && jsonObject.getBoolean("liveMode");
                String orderCode = jsonObject.has("orderCode") ? jsonObject.getString("orderCode") : "";
                String projId = jsonObject.has("projId") ? jsonObject.getString("projId") : "";
                String bizId = jsonObject.has("bizId") ? jsonObject.getString("bizId") : "";
                String stage = jsonObject.has("stage") ? jsonObject.getString("stage") : "";

                String replaceable = String.format("[%s, \\s.]", NumberFormat.getCurrencyInstance(Locale.CHINA).getCurrency().getSymbol(Locale.CHINA));
                String cleanString = amountText.replaceAll(replaceable, "");
                int amount = Integer.valueOf(new BigDecimal(cleanString).toString());

                String URL = chargeServerURL + "&channel=" + type + "&amount=" + amountText + "&subject=" + subject + "&body=" + body + "&liveMode=" + liveMode + "&orderCode=" + orderCode + "&projId=" + projId + "&bizId=" + bizId + "&stage=" + stage;

                PaymentRequestInfo info = new PaymentRequestInfo(type, amount);
                String infoJson = new Gson().toJson(info);

                charge = postJson(URL, infoJson);

                if (TextUtils.isEmpty(charge) || charge.contains("错误提示")) {
                    Toast.makeText(cordova.getActivity(), "请查看服务端日志,检查相关配置是否正确", Toast.LENGTH_LONG).show();
                    callbackContext.error("请查看服务端日志,检查相关配置是否正确");
                }
                Log.i(TAG, "charge = " + charge);

                Intent intent = new Intent(this.cordova.getActivity(), PaymentActivity.class);
                intent.putExtra(PaymentActivity.EXTRA_CHARGE, charge);
                this.cordova.startActivityForResult(this, intent, Pingpp.REQUEST_CODE_PAYMENT);
                return true;
            }
        }
        return false;
    }


    private String postJson(String url, String json) {
        MediaType type = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(type, json);
        Request request = new Request.Builder().url(url).post(body).build();
        OkHttpClient client = new OkHttpClient();
        try {
            return Objects.requireNonNull(client.newCall(request).execute().body()).string();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "requestCode = " + requestCode + "--- resultCode = " + resultCode);
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            String result = Objects.requireNonNull(data.getExtras()).getString("pay_result");
            String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
            String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
            Log.i(TAG, "result = " + result + " --- errorMsg = " + errorMsg + " --- extraMsg = " + extraMsg);
            try {
                JSONObject object = new JSONObject();
                object.put("error", errorMsg);
                object.put("extra", extraMsg);
                object.put("result", result);
                callbackContext.success(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class PaymentRequestInfo {
        String channel;
        int amount;

        PaymentRequestInfo(String channel, int amount) {
            this.channel = channel;
            this.amount = amount;
        }
    }
}
