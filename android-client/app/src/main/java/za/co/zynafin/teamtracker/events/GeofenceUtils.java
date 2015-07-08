/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package za.co.zynafin.teamtracker.events;

import com.google.android.gms.location.Geofence;

import za.co.zynafin.teamtracker.R;
import za.co.zynafin.teamtracker.account.Constants;

/**
 * This class defines constants used by location sample apps.
 */
public final class GeofenceUtils {

    public static final String TAG = "GEOFENCE";

    public static final int DEFAULT_LOITERING_PERIOD_IN_MS = 240 * 1000; //4 minutes

    //todo: make this a larger # for prod to conserve battery
    public static final int DEFAULT_NOTIFICATION_RESPONSIVENESS_MS = 10 * 1000;    //10 sec ~ 3 min for prod
    public enum REMOVE_TYPE {
        INTENT, LIST

    }
    public enum REQUEST_TYPE {
        ADD, REMOVE

    }

    // Intent actions
    public static final String ACTION_CONNECTION_ERROR =
            "za.co.zynafin.teamtracker.ACTION_CONNECTION_ERROR";

    public static final String ACTION_CONNECTION_SUCCESS =
            "za.co.zynafin.teamtracker.ACTION_CONNECTION_SUCCESS";

    public static final String ACTION_GEOFENCES_ADDED =
            "za.co.zynafin.teamtracker.ACTION_GEOFENCES_ADDED";

    public static final String ACTION_GEOFENCES_REMOVED =
            "za.co.zynafin.teamtracker.ACTION_GEOFENCES_DELETED";

    public static final String ACTION_GEOFENCE_ERROR =
            "za.co.zynafin.teamtracker.ACTION_GEOFENCES_ERROR";

    public static final String ACTION_GEOFENCE_TRANSITION =
            "za.co.zynafin.teamtracker.ACTION_GEOFENCE_TRANSITION";

    public static final String ACTION_GEOFENCE_TRANSITION_ERROR =
            "za.co.zynafin.teamtracker.ACTION_GEOFENCE_TRANSITION_ERROR";

    // The Intent category used by all Location Services sample apps
    public static final String CATEGORY_LOCATION_SERVICES =
            "za.co.zynafin.teamtracker.CATEGORY_LOCATION_SERVICES";

    // Keys for extended data in Intents
    public static final String EXTRA_CONNECTION_CODE =
            "za.co.zynafin.teamtracker.EXTRA_CONNECTION_CODE";

    public static final String EXTRA_CONNECTION_ERROR_CODE =
            "za.co.zynafin.teamtracker.EXTRA_CONNECTION_ERROR_CODE";

    public static final String EXTRA_CONNECTION_ERROR_MESSAGE =
            "za.co.zynafin.teamtracker.EXTRA_CONNECTION_ERROR_MESSAGE";

    public static final String EXTRA_GEOFENCE_STATUS =
            "za.co.zynafin.teamtracker.EXTRA_GEOFENCE_STATUS";

    public static final String EXTRA_GEOFENCE_EVENT =
            "za.co.zynafin.teamtracker.GEOFENCE_EVENT";

    public final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    public static String getTransitionString(int transitionType) {
        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                return Constants.TRANSISTION_TYPE_ENTER;
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                return Constants.TRANSISTION_TYPE_EXIT;
            case Geofence.GEOFENCE_TRANSITION_DWELL:
                return Constants.TRANSISTION_TYPE_DWELL;
            default:
                return "UNKNOWN";
        }
    }

}
