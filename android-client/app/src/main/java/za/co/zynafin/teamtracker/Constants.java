/**
 * Copyright 2014 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package za.co.zynafin.teamtracker;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

import za.co.zynafin.teamtracker.account.AuthToken;

/**
 * Constants used in this sample.
 */
public final class Constants {

    private Constants() {
    }

    public static final String PACKAGE_NAME = "za.cco.zynafin.teamtracker";

    public static final String SHARED_PREFERENCES_NAME = PACKAGE_NAME + ".SHARED_PREFERENCES_NAME";

    public static final String GEOFENCES_ADDED_KEY = PACKAGE_NAME + ".GEOFENCES_ADDED_KEY";

    /**
     * Used to set an expiration time for a geofence. After this amount of time Location Services
     * stops tracking the geofence.
     */
    public static final long GEOFENCE_EXPIRATION_IN_HOURS = 12;

    /**
     * For this sample, geofences expire after twelve hours.
     */
    public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS =
            GEOFENCE_EXPIRATION_IN_HOURS * 60 * 60 * 1000;
    public static final float GEOFENCE_RADIUS_IN_METERS = 200; // 1 mile, 1.6 km

    /**
     * Map for storing information about airports in the San Francisco bay area.
     */
    public static final HashMap<String, LatLng> BAY_AREA_LANDMARKS = new HashMap<String, LatLng>();
    static {
        BAY_AREA_LANDMARKS.put("Zynafin", new LatLng(-26.69837, 27.11922));
        BAY_AREA_LANDMARKS.put("NWU", new LatLng(37.422611, -122.0840577));
        BAY_AREA_LANDMARKS.put("Mooirivier Laerskool", new LatLng(-26.6945538, 27.0935404));
        BAY_AREA_LANDMARKS.put("Pick & Pay", new LatLng(-26.695663276596207, 27.11258411407470));
    }

    protected static final String TAG = "TeamTracker";


    public static AuthToken AUTH_TOKEN;
}
