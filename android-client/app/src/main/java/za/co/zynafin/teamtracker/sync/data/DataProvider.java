package za.co.zynafin.teamtracker.sync.data;

//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;


public class DataProvider {
// extends ContentProvider {

//	TeamTrackerDatabase teamTrackerDatabase;
//
//	private static final String AUTHORITY = Constants.CONTENT_AUTHORITY;
//
//	// The constants below represent individual URI routes, as IDs. Every URI pattern recognized by
//	// this ContentProvider is defined using sUriMatcher.addURI(), and associated with one of these
//	// IDs.
//	//
//	// When a incoming URI is run through sUriMatcher, it will be tested against the defined
//	// URI patterns, and the corresponding route ID will be returned.
//	/**
//	 * URI ID for route: /entries
//	 */
//	public static final int ROUTE_GEO_FENCES = 1;
//	public static final int ROUTE_GEO_FENCE_ID = 2;
//    public static final int ROUTE_GEO_FENCE_EVENTS = 3;
//    public static final int ROUTE_GEO_FENCE_EVENT_ID = 4;
//
//	private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
//
//	static {
//		sUriMatcher.addURI(AUTHORITY, "geofences", ROUTE_GEO_FENCES);
//		sUriMatcher.addURI(AUTHORITY, "geofences/*", ROUTE_GEO_FENCE_ID);
//		sUriMatcher.addURI(AUTHORITY, "geofenceevents", ROUTE_GEO_FENCE_EVENTS);
//		sUriMatcher.addURI(AUTHORITY, "geofenceevents/*", ROUTE_GEO_FENCE_EVENT_ID);
//	}
//
//	@Override
//	public boolean onCreate() {
//		teamTrackerDatabase = new TeamTrackerDatabase(getContext());
//		return true;
//	}
//
//	@Override
//	public String getType(Uri uri) {
//		final int match = sUriMatcher.match(uri);
//		switch (match) {
//			case ROUTE_GEO_FENCES:
//				return Constants.GeoFenceColumns.CONTENT_TYPE;
//			case ROUTE_GEO_FENCE_ID:
//				return Constants.GeoFenceColumns.CONTENT_ITEM_TYPE;
//            case ROUTE_GEO_FENCE_EVENTS:
//                return Constants.GeoFenceEventColumns.CONTENT_TYPE;
//            case ROUTE_GEO_FENCE_EVENT_ID:
//                return Constants.GeoFenceEventColumns.CONTENT_ITEM_TYPE;
//			default:
//				throw new UnsupportedOperationException("Unknown uri: " + uri);
//		}
//	}
//
//	@Override
//	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
//	                    String sortOrder) {
//		SQLiteDatabase db = teamTrackerDatabase.getReadableDatabase();
//		SelectionBuilder builder = new SelectionBuilder();
//		int uriMatch = sUriMatcher.match(uri);
//        Cursor c;
//        Context ctx = getContext();
//		switch (uriMatch) {
//			case ROUTE_GEO_FENCE_ID:
//				// Return a single entry, by ID.
//                builder.table(Constants.TABLE_GEO_FENCE);
//				String id = uri.getLastPathSegment();
//				builder.where(Constants.GeoFenceColumns._ID + "=?", id);
//			case ROUTE_GEO_FENCES:
//				// Return all known entries.
//				builder.table(Constants.TABLE_GEO_FENCE);
////					.where(selection, selectionArgs);
//				c = builder.query(db, projection, sortOrder);
//				// Note: Notification URI must be manually set here for loaders to correctly
//				// register ContentObservers.
//				assert ctx != null;
//				c.setNotificationUri(ctx.getContentResolver(), uri);
//				Log.v("GEO", "GeoFences returned: " + c.getCount());
//				return c;
//            case ROUTE_GEO_FENCE_EVENT_ID:
//                // Return a single entry, by ID.
//                builder.table(Constants.TABLE_GEO_FENCE_EVENT);
//                String eventId = uri.getLastPathSegment();
//                builder.where(Constants.GeoFenceEventColumns._ID + "=?", eventId);
//            case ROUTE_GEO_FENCE_EVENTS:
//                // Return all known entries.
//                builder.table(Constants.TABLE_GEO_FENCE_EVENT);
////					.where(selection, selectionArgs);
//                c = builder.query(db, projection, sortOrder);
//                // Note: Notification URI must be manually set here for loaders to correctly
//                // register ContentObservers.
//                assert ctx != null;
//                c.setNotificationUri(ctx.getContentResolver(), uri);
//                Log.v("DATAPROVIDER", "GeoFenceEvents returned: " + c.getCount());
//                return c;
//			default:
//				throw new UnsupportedOperationException("Unknown uri: " + uri);
//		}
//	}
//
//	@Override
//	public Uri insert(Uri uri, ContentValues values) {
//		final SQLiteDatabase db = teamTrackerDatabase.getWritableDatabase();
//		assert db != null;
//		final int match = sUriMatcher.match(uri);
//		Uri result;
//        long id;
//        switch (match) {
//			case ROUTE_GEO_FENCES:
//				id = db.insertOrThrow(Constants.TABLE_GEO_FENCE, null, values);
//				result = Uri.parse(Constants.GeoFenceColumns.CONTENT_URI + "/" + id);
//				break;
//            case ROUTE_GEO_FENCE_EVENTS:
//                id = db.insertOrThrow(Constants.TABLE_GEO_FENCE_EVENT, null, values);
//                result = Uri.parse(Constants.GeoFenceEventColumns.CONTENT_URI + "/" + id);
//                break;
//			default:
//				throw new UnsupportedOperationException("Insert not supported on uri: " + uri);
//		}
//		// Send broadcast to registered ContentObservers, to refresh UI.
//		Context ctx = getContext();
//		assert ctx != null;
//		ctx.getContentResolver().notifyChange(uri, null, false);
//		return result;
//	}
//
//	@Override
//	public int delete(Uri uri, String selection, String[] selectionArgs) {
//		SelectionBuilder builder = new SelectionBuilder();
//		final SQLiteDatabase db = teamTrackerDatabase.getWritableDatabase();
//		final int match = sUriMatcher.match(uri);
//		int count;
//        String id;
//		switch (match) {
//			case ROUTE_GEO_FENCES:
//				count = builder.table(Constants.TABLE_GEO_FENCE)
//					.where(selection, selectionArgs)
//					.delete(db);
//				break;
//			case ROUTE_GEO_FENCE_ID:
//				id = uri.getLastPathSegment();
//				count = builder.table(Constants.TABLE_GEO_FENCE)
//					.where(Constants.GeoFenceColumns._ID + "=?", id)
//					.where(selection, selectionArgs)
//					.delete(db);
//				break;
//            case ROUTE_GEO_FENCE_EVENTS:
//                count = builder.table(Constants.TABLE_GEO_FENCE_EVENT)
//                        .where(selection, selectionArgs)
//                        .delete(db);
//                break;
//            case ROUTE_GEO_FENCE_EVENT_ID:
//                id = uri.getLastPathSegment();
//                count = builder.table(Constants.TABLE_GEO_FENCE)
//                        .where(Constants.GeoFenceEventColumns._ID + "=?", id)
//                        .where(selection, selectionArgs)
//                        .delete(db);
//                break;
//			default:
//				throw new UnsupportedOperationException("Unknown uri: " + uri);
//		}
//		// Send broadcast to registered ContentObservers, to refresh UI.
//		Context ctx = getContext();
//		assert ctx != null;
//		ctx.getContentResolver().notifyChange(uri, null, false);
//		return count;
//	}
//
//	@Override
//	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
//		SelectionBuilder builder = new SelectionBuilder();
//		final SQLiteDatabase db = teamTrackerDatabase.getWritableDatabase();
//		final int match = sUriMatcher.match(uri);
//		int count;
//        String id;
//		switch (match) {
//			case ROUTE_GEO_FENCES:
//				count = builder.table(Constants.TABLE_GEO_FENCE)
//					.where(selection, selectionArgs)
//					.update(db, values);
//				break;
//			case ROUTE_GEO_FENCE_ID:
//				id = uri.getLastPathSegment();
//				count = builder.table(Constants.TABLE_GEO_FENCE)
//					.where(Constants.GeoFenceColumns._ID + "=?", id)
//					.where(selection, selectionArgs)
//					.update(db, values);
//				break;
//            case ROUTE_GEO_FENCE_EVENTS:
//                count = builder.table(Constants.TABLE_GEO_FENCE_EVENT)
//                        .where(selection, selectionArgs)
//                        .update(db, values);
//                break;
//            case ROUTE_GEO_FENCE_EVENT_ID:
//                id = uri.getLastPathSegment();
//                count = builder.table(Constants.TABLE_GEO_FENCE_EVENT)
//                        .where(Constants.GeoFenceEventColumns._ID + "=?", id)
//                        .where(selection, selectionArgs)
//                        .update(db, values);
//                break;
//			default:
//				throw new UnsupportedOperationException("Unknown uri: " + uri);
//		}
//		Context ctx = getContext();
//		assert ctx != null;
//		ctx.getContentResolver().notifyChange(uri, null, false);
//		return count;
//	}
//
//
//	private class SelectionBuilder {
//		private static final String TAG = "GEO_SELECTION_B";
//
//		private String mTable = null;
//		private Map<String, String> mProjectionMap = Maps.newHashMap();
//		private StringBuilder mSelection = new StringBuilder();
//		private ArrayList<String> mSelectionArgs = Lists.newArrayList();
//
//		/**
//		 * Reset any internal state, allowing this builder to be recycled.
//		 */
//		public SelectionBuilder reset() {
//			mTable = null;
//			mSelection.setLength(0);
//			mSelectionArgs.clear();
//			return this;
//		}
//
//		/**
//		 * Append the given selection clause to the internal state. Each clause is
//		 * surrounded with parenthesis and combined using {@code AND}.
//		 */
//		public SelectionBuilder where(String selection, String... selectionArgs) {
//			if (TextUtils.isEmpty(selection)) {
//				if (selectionArgs != null && selectionArgs.length > 0) {
//					throw new IllegalArgumentException(
//						"Valid selection required when including arguments=");
//				}
//
//				// Shortcut when clause is empty
//				return this;
//			}
//
//			if (mSelection.length() > 0) {
//				mSelection.append(" AND ");
//			}
//
//			mSelection.append("(").append(selection).append(")");
//			if (selectionArgs != null) {
//				Collections.addAll(mSelectionArgs, selectionArgs);
//			}
//
//			return this;
//		}
//
//		public SelectionBuilder table(String table) {
//			mTable = table;
//			return this;
//		}
//
//		private void assertTable() {
//			if (mTable == null) {
//				throw new IllegalStateException("Table not specified");
//			}
//		}
//
//		public SelectionBuilder mapToTable(String column, String table) {
//			mProjectionMap.put(column, table + "." + column);
//			return this;
//		}
//
//		public SelectionBuilder map(String fromColumn, String toClause) {
//			mProjectionMap.put(fromColumn, toClause + " AS " + fromColumn);
//			return this;
//		}
//
//		/**
//		 * Return selection string for current internal state.
//		 *
//		 * @see #getSelectionArgs()
//		 */
//		public String getSelection() {
//			return mSelection.toString();
//		}
//
//		/**
//		 * Return selection arguments for current internal state.
//		 *
//		 * @see #getSelection()
//		 */
//		public String[] getSelectionArgs() {
//			return mSelectionArgs.toArray(new String[mSelectionArgs.size()]);
//		}
//
//		private void mapColumns(String[] columns) {
//			for (int i = 0; i < columns.length; i++) {
//				final String target = mProjectionMap.get(columns[i]);
//				if (target != null) {
//					columns[i] = target;
//				}
//			}
//		}
//
//		@Override
//		public String toString() {
//			return "SelectionBuilder[table=" + mTable + ", selection=" + getSelection()
//				+ ", selectionArgs=" + Arrays.toString(getSelectionArgs()) + "]";
//		}
//
//		/**
//		 * Execute query using the current internal state as {@code WHERE} clause.
//		 */
//		public Cursor query(SQLiteDatabase db, String[] columns, String orderBy) {
//			return query(db, columns, null, null, orderBy, null);
//		}
//
//		/**
//		 * Execute query using the current internal state as {@code WHERE} clause.
//		 */
//		public Cursor query(SQLiteDatabase db, String[] columns, String groupBy,
//		                    String having, String orderBy, String limit) {
//			assertTable();
//			if (columns != null) mapColumns(columns);
//			Log.v(TAG, "query(columns=" + Arrays.toString(columns) + ") " + this);
//			return db.query(mTable, columns, getSelection(), getSelectionArgs(), groupBy, having,
//				orderBy, limit);
//		}
//
//		/**
//		 * Execute update using the current internal state as {@code WHERE} clause.
//		 */
//		public int update(SQLiteDatabase db, ContentValues values) {
//			assertTable();
//			Log.v(TAG, "update() " + this);
//			return db.update(mTable, values, getSelection(), getSelectionArgs());
//		}
//
//		/**
//		 * Execute delete using the current internal state as {@code WHERE} clause.
//		 */
//		public int delete(SQLiteDatabase db) {
//			assertTable();
//			Log.v(TAG, "delete() " + this);
//			return db.delete(mTable, getSelection(), getSelectionArgs());
//		}
//	}
}
