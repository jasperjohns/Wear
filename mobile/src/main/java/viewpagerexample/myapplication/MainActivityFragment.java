package viewpagerexample.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{
    private final String LOG_TAG = "Wear";

    GoogleApiClient mGoogleApiClient;

    public MainActivityFragment() {
    }

    /*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
*/



    @Override
    public void onStart() {

        Log.v(LOG_TAG, "OnStart");

        super.onStart();
        /*
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        */
        mGoogleApiClient.connect();
    }

    @Override
    public  void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.v(LOG_TAG, "OnAttach");

        mGoogleApiClient = new GoogleApiClient.Builder(activity)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
       // mGoogleApiClient.connect();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mGoogleApiClient.disconnect();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        rootView.findViewById(R.id.wearable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.v(LOG_TAG, "OnClick1");




/*

                if(mGoogleApiClient.isConnected()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).await();
                            for(Node node : nodes.getNodes()) {
                                MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(mGoogleApiClient, node.getId(),
                                        "/startactivity", "Hello World".getBytes()).await();
                                if(!result.getStatus().isSuccess()){
                                    Log.v(LOG_TAG, "error");

                                } else {
                                    Log.v(LOG_TAG, "success!! sent to: " + node.getDisplayName());
                                }
                            }
                        }
                    }).start();

                } else {
                    Log.v(LOG_TAG, "not connected");
                }
*/

            }
        });
        rootView.findViewById(R.id.oldschool).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationManager man = (NotificationManager)getActivity().getSystemService( getActivity().NOTIFICATION_SERVICE);
                man.notify(0, getNotification().build());
            }
        });
        return rootView;
    }

    NotificationCompat.Builder getNotification() {
        Intent viewIntent = new Intent(getActivity(), MainActivity.class);
//            viewIntent.putExtra(EXTRA_EVENT_ID, eventId);
        PendingIntent viewPendingIntent = PendingIntent.getActivity(getActivity(), 0, viewIntent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity());
        builder.setLargeIcon(BitmapFactory.decodeResource(getActivity().getResources(), R.mipmap.ic_launcher));
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setSubText("Its Bed Time");
        builder.setContentTitle("Wear Notification Test");
        builder.setContentText("Wear Notification Text");
//            builder.setContentIntent(viewPendingIntent);
        return builder;
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(LOG_TAG, "onConnected");

        if(mGoogleApiClient.isConnected()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).await();
                    for(Node node : nodes.getNodes()) {
                        MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(mGoogleApiClient, node.getId(),
                                "/startactivity", "Hello World".getBytes()).await();
                        if(!result.getStatus().isSuccess()){
                            Log.v(LOG_TAG, "error");

                        } else {
                            Log.v(LOG_TAG, "success!! sent to: " + node.getDisplayName());
                        }
                    }
                }
            }).start();

        } else {
            Log.v(LOG_TAG, "not connected");
        }


    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(LOG_TAG, "Failed to connect to Google API Client" + connectionResult.getErrorMessage());
    }


}
