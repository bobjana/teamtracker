/*
 * Copyright 2013 The Android Open Source Project
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

package za.co.zynafin.teamtracker.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;


/**
 * Define a sync adapter for the app.
 * <p/>
 * <p>This class is instantiated in {@link SyncService}, which also binds SyncAdapter to the system.
 * SyncAdapter should only be initialized in SyncService, never anywhere else.
 * <p/>
 * <p>The system calls onPerformSync() via an RPC call through the IBinder object supplied by
 * SyncService.
 */
class SyncAdapter extends AbstractThreadedSyncAdapter {

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient contentProviderClient, SyncResult syncResult) {

    }


    //    public static final String TAG = "ADAPTER";
//    private final ContentResolver mContentResolver;
//    private RestTemplate restTemplate = new RestTemplate();
//    private GeofenceRequester mGeofenceRequester;
//    private GeofenceRemover mGeofenceRemover;
//
//    public SyncAdapter(Context context, boolean autoInitialize) {
//        super(context, autoInitialize);
//        mContentResolver = context.getContentResolver();
//        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
//        mGeofenceRequester = new GeofenceRequester(context);
//        mGeofenceRemover = new GeofenceRemover(context);
//    }
//
//    @Override
//    public void onPerformSync(Account account, Bundle extras, String authority,
//                              ContentProviderClient provider, SyncResult syncResult) {
//        Log.i(TAG, "Beginning network synchronization");
//        syncLocalGeoFences(syncResult);
//        syncGeoFenceEvents(syncResult);
//        Log.i(TAG, "Network synchronization complete");
//    }
//
//
//    private void syncLocalGeoFences(SyncResult syncResult) {
//        Log.i(TAG, "Beginning geo-fence synchronization");
//        final String url = Config.BASE_SERVER_URL + Config.GEO_FENCE_LIST_PATH;
//        try {
//            GeoFence[] geoFences = restTemplate.getForObject(url, GeoFence[].class);
//            Log.i(TAG, String.format("Retrieved '%s' geo-fences from server", geoFences.length));
//            //todo: cleanup old registered geofences
////            mGeofenceRemover.setInProgressFlag(false);
////            mGeofenceRemover.removeGeofencesByIntent(mGeofenceRequester.getRequestPendingIntent());
////
////            while(mGeofenceRemover.getInProgressFlag()) {
////                Thread.sleep(300);
////            }
//            mGeofenceRequester.setInProgressFlag(false);
//            mGeofenceRequester.addGeofences(Arrays.asList(geoFences));
//
//            syncResult.stats.numInserts = geoFences.length;
//
//        }
//        catch (Exception e){
//            Log.e(TAG, "Error performing geo fence sync", e);
//            syncResult.stats.numParseExceptions++;
//            return;
//        }
//        Log.i(TAG, "geo-fence synchronization complete");
//    }
//
////    private void syncGeoFences(SyncResult syncResult) {
////        Log.i(TAG, "Beginning geo-fence synchronization");
////        final String url = Config.BASE_SERVER_URL + Config.GEO_FENCE_LIST_PATH;
////        try {
////            GeoFence[] geoFences = restTemplate.getForObject(url, GeoFence[].class);
////
////            final ContentResolver contentResolver = getContext().getContentResolver();
////            ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
////
////            // Build hash table of incoming entries
////            HashMap<Long, GeoFence> remoteGeoFenceMap = new HashMap<Long, GeoFence>();
////            for (GeoFence geoFence : Arrays.asList(geoFences)) {
////                remoteGeoFenceMap.put(geoFence.getId(), geoFence);
////            }
////
////            // Get list of all items
////            Log.i(TAG, "Fetching local entries for merge");
////            Uri uri = Constants.GeoFenceColumns.CONTENT_URI;
////            Cursor c = contentResolver.query(uri, new String[]{
////                    Constants.GeoFenceColumns._ID,
////                    Constants.GeoFenceColumns.COLUMN_NAME_LAT,
////                    Constants.GeoFenceColumns.COLUMN_NAME_LNG,
////                    Constants.GeoFenceColumns.COLUMN_NAME_RADIUS}, null, null, null);
////            assert c != null;
////            Log.i(TAG, "Found " + c.getCount() + " local entries. Computing merge solution...");
////            List<String> geoFenceRemoveList = new ArrayList<String>();
////            long id;
////            while (c.moveToNext()) {
////                syncResult.stats.numEntries++;
////                id = c.getLong(0);
////                if (remoteGeoFenceMap.containsKey(id)) {
////                    //update
////                    GeoFence updatedGeoFence = new GeoFence(id, c.getDouble(1), c.getDouble(2), c.getFloat(3));
////                    Uri existingUri = Constants.GeoFenceColumns.CONTENT_URI.buildUpon().appendPath(Long.toString(id)).build();
////                    if (!remoteGeoFenceMap.get(id).equals(updatedGeoFence)) {
////                        batch.add(updateGeoFence(existingUri, updatedGeoFence));
////                        syncResult.stats.numUpdates++;
////                    }
////                    remoteGeoFenceMap.remove(id);
////                } else {
////                    //delete
////                    geoFenceRemoveList.add(Long.toString(id));
////
////                    Uri deleteUri = Constants.GeoFenceColumns.CONTENT_URI.buildUpon().appendPath(Long.toString(id)).build();
////                    batch.add(deleteGeoFence(deleteUri));
////                    syncResult.stats.numDeletes++;
////                }
////            }
////            c.close();
////
////            // Add new items
////            for (GeoFence newGeoFence : remoteGeoFenceMap.values()) {
////                batch.add(addGeoFence(newGeoFence));
////                syncResult.stats.numInserts++;
////            }
////            Log.i(TAG, "Merge solution ready. Applying batch update");
////
////            mContentResolver.applyBatch(Constants.CONTENT_AUTHORITY, batch);
////            mContentResolver.notifyChange(Constants.GeoFenceColumns.CONTENT_URI, // URI where data was modified
////                    null,                           // No local observer
////                    false);                         // IMPORTANT: Do not sync to network
////            // This sample doesn't support uploads, but if *your* code does, make sure you set
////            // syncToNetwork=false in the line above to prevent duplicate syncs.
////        } catch (Exception e) {
////            Log.e(TAG, "Error performing geo fence sync", e);
////            syncResult.stats.numParseExceptions++;
////            return;
////        }
////        Log.i(TAG, "geo-fence synchronization complete");
////    }
//
//    private void syncGeoFenceEvents(SyncResult syncResult) {
//        try {
//            ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
//
//            Cursor c = mContentResolver.query(Constants.GeoFenceEventColumns.CONTENT_URI,
//                    new String[]{
//                            Constants.GeoFenceEventColumns._ID,
//                            Constants.GeoFenceEventColumns.COLUMN_TYPE,
//                            Constants.GeoFenceEventColumns.COLUMN_GEO_FENCE_ID,
//                            Constants.GeoFenceEventColumns.COLUMN_DATE,
//                    }, null, null, null
//            );
//            assert c != null;
//            Log.i("TEST", "Found " + c.getCount() + " local events to be synched.");
//
//            List<GeoFenceEvent> geoFenceEvents = new ArrayList<GeoFenceEvent>();
//            while (c.moveToNext()) {
//                GeoFenceEvent event = new GeoFenceEvent(c.getString(1), new GeoFence(c.getLong(2)), new Date(c.getLong(3)));
//                //todo: derive userId from account
//                event.setUser(new User(1l));
//                restTemplate.postForLocation(Config.BASE_SERVER_URL + Config.EVENT_SAVE_PATH, event, GeoFenceEvent.class);
//                geoFenceEvents.add(event);
//            }
////            restTemplate.postForLocation(Config.BASE_SERVER_URL + Config.EVENT_SAVE_ALL_PATH, geoFenceEvents, GeoFenceEvent.class);
//
//            Uri deleteUri = Constants.GeoFenceEventColumns.CONTENT_URI.buildUpon().build();
//            batch.add(deleteGeoFence(deleteUri));
//
//            mContentResolver.applyBatch(Constants.CONTENT_AUTHORITY, batch);
//            mContentResolver.notifyChange(Constants.GeoFenceEventColumns.CONTENT_URI, null, false);
//        } catch (Exception e) {
//            Log.e(TAG, "Error performing geo fence event sync", e);
//            syncResult.stats.numParseExceptions++;
//            return;
//        }
//    }
//
//    private ContentProviderOperation addGeoFence(GeoFence newGeoFence) {
//        Log.i(TAG, "Scheduling insert: location_id=" + newGeoFence.getId());
//        return ContentProviderOperation.newInsert(Constants.GeoFenceColumns.CONTENT_URI)
//                .withValue(Constants.GeoFenceColumns.COLUMN_NAME_LAT, newGeoFence.getLat())
//                .withValue(Constants.GeoFenceColumns.COLUMN_NAME_LNG, newGeoFence.getLng())
//                .withValue(Constants.GeoFenceColumns.COLUMN_NAME_RADIUS, newGeoFence.getRadius())
//                .build();
//    }
//
//    private ContentProviderOperation updateGeoFence(Uri existingUri, GeoFence geoFence) {
//        Log.i(TAG, "Scheduling update: " + existingUri);
//        return ContentProviderOperation.newUpdate(existingUri)
//                .withValue(Constants.GeoFenceColumns.COLUMN_NAME_LAT, geoFence.getLat())
//                .withValue(Constants.GeoFenceColumns.COLUMN_NAME_LNG, geoFence.getLng())
//                .withValue(Constants.GeoFenceColumns.COLUMN_NAME_RADIUS, geoFence.getRadius())
//                .build();
//    }
//
//    private ContentProviderOperation deleteGeoFence(Uri deleteUri) {
//        Log.i(TAG, "Scheduling delete: " + deleteUri);
//        return ContentProviderOperation.newDelete(deleteUri).build();
//    }
//
//    private ContentProviderOperation deleteGeoFenceEvents(Uri deleteUri) {
//        Log.i(TAG, "Scheduling delete: " + deleteUri);
//        return ContentProviderOperation.newDelete(deleteUri).build();
//    }

}
