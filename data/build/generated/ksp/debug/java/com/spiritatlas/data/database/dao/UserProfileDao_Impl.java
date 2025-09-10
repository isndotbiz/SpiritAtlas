package com.spiritatlas.data.database.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.spiritatlas.data.database.entities.UserProfileEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class UserProfileDao_Impl implements UserProfileDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<UserProfileEntity> __insertionAdapterOfUserProfileEntity;

  private final EntityDeletionOrUpdateAdapter<UserProfileEntity> __updateAdapterOfUserProfileEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteProfile;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllProfiles;

  private final SharedSQLiteStatement __preparedStmtOfMarkProfileAsSynced;

  private final SharedSQLiteStatement __preparedStmtOfUpdateProfileCompletion;

  public UserProfileDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUserProfileEntity = new EntityInsertionAdapter<UserProfileEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `user_profiles` (`id`,`profileName`,`createdAt`,`lastModified`,`name`,`displayName`,`birthDateTime`,`birthPlace`,`middleName`,`nickname`,`spiritualName`,`motherName`,`fatherName`,`ancestry`,`gender`,`bloodType`,`dominantHand`,`eyeColor`,`firstBreath`,`weatherConditions`,`moonPhase`,`hospitalName`,`firstWord`,`firstSteps`,`preferences`,`profileCompletion`,`enrichmentResult`,`enrichmentGeneratedAt`,`updatedAt`,`syncStatus`,`encryptionVersion`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UserProfileEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getProfileName());
        statement.bindString(3, entity.getCreatedAt());
        statement.bindString(4, entity.getLastModified());
        if (entity.getName() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getName());
        }
        if (entity.getDisplayName() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getDisplayName());
        }
        if (entity.getBirthDateTime() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getBirthDateTime());
        }
        if (entity.getBirthPlace() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getBirthPlace());
        }
        if (entity.getMiddleName() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getMiddleName());
        }
        if (entity.getNickname() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getNickname());
        }
        if (entity.getSpiritualName() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getSpiritualName());
        }
        if (entity.getMotherName() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getMotherName());
        }
        if (entity.getFatherName() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getFatherName());
        }
        if (entity.getAncestry() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getAncestry());
        }
        if (entity.getGender() == null) {
          statement.bindNull(15);
        } else {
          statement.bindString(15, entity.getGender());
        }
        if (entity.getBloodType() == null) {
          statement.bindNull(16);
        } else {
          statement.bindString(16, entity.getBloodType());
        }
        if (entity.getDominantHand() == null) {
          statement.bindNull(17);
        } else {
          statement.bindString(17, entity.getDominantHand());
        }
        if (entity.getEyeColor() == null) {
          statement.bindNull(18);
        } else {
          statement.bindString(18, entity.getEyeColor());
        }
        if (entity.getFirstBreath() == null) {
          statement.bindNull(19);
        } else {
          statement.bindString(19, entity.getFirstBreath());
        }
        if (entity.getWeatherConditions() == null) {
          statement.bindNull(20);
        } else {
          statement.bindString(20, entity.getWeatherConditions());
        }
        if (entity.getMoonPhase() == null) {
          statement.bindNull(21);
        } else {
          statement.bindString(21, entity.getMoonPhase());
        }
        if (entity.getHospitalName() == null) {
          statement.bindNull(22);
        } else {
          statement.bindString(22, entity.getHospitalName());
        }
        if (entity.getFirstWord() == null) {
          statement.bindNull(23);
        } else {
          statement.bindString(23, entity.getFirstWord());
        }
        if (entity.getFirstSteps() == null) {
          statement.bindNull(24);
        } else {
          statement.bindString(24, entity.getFirstSteps());
        }
        if (entity.getPreferences() == null) {
          statement.bindNull(25);
        } else {
          statement.bindString(25, entity.getPreferences());
        }
        if (entity.getProfileCompletion() == null) {
          statement.bindNull(26);
        } else {
          statement.bindString(26, entity.getProfileCompletion());
        }
        if (entity.getEnrichmentResult() == null) {
          statement.bindNull(27);
        } else {
          statement.bindString(27, entity.getEnrichmentResult());
        }
        if (entity.getEnrichmentGeneratedAt() == null) {
          statement.bindNull(28);
        } else {
          statement.bindString(28, entity.getEnrichmentGeneratedAt());
        }
        statement.bindLong(29, entity.getUpdatedAt());
        statement.bindString(30, entity.getSyncStatus());
        statement.bindLong(31, entity.getEncryptionVersion());
      }
    };
    this.__updateAdapterOfUserProfileEntity = new EntityDeletionOrUpdateAdapter<UserProfileEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `user_profiles` SET `id` = ?,`profileName` = ?,`createdAt` = ?,`lastModified` = ?,`name` = ?,`displayName` = ?,`birthDateTime` = ?,`birthPlace` = ?,`middleName` = ?,`nickname` = ?,`spiritualName` = ?,`motherName` = ?,`fatherName` = ?,`ancestry` = ?,`gender` = ?,`bloodType` = ?,`dominantHand` = ?,`eyeColor` = ?,`firstBreath` = ?,`weatherConditions` = ?,`moonPhase` = ?,`hospitalName` = ?,`firstWord` = ?,`firstSteps` = ?,`preferences` = ?,`profileCompletion` = ?,`enrichmentResult` = ?,`enrichmentGeneratedAt` = ?,`updatedAt` = ?,`syncStatus` = ?,`encryptionVersion` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UserProfileEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getProfileName());
        statement.bindString(3, entity.getCreatedAt());
        statement.bindString(4, entity.getLastModified());
        if (entity.getName() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getName());
        }
        if (entity.getDisplayName() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getDisplayName());
        }
        if (entity.getBirthDateTime() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getBirthDateTime());
        }
        if (entity.getBirthPlace() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getBirthPlace());
        }
        if (entity.getMiddleName() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getMiddleName());
        }
        if (entity.getNickname() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getNickname());
        }
        if (entity.getSpiritualName() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getSpiritualName());
        }
        if (entity.getMotherName() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getMotherName());
        }
        if (entity.getFatherName() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getFatherName());
        }
        if (entity.getAncestry() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getAncestry());
        }
        if (entity.getGender() == null) {
          statement.bindNull(15);
        } else {
          statement.bindString(15, entity.getGender());
        }
        if (entity.getBloodType() == null) {
          statement.bindNull(16);
        } else {
          statement.bindString(16, entity.getBloodType());
        }
        if (entity.getDominantHand() == null) {
          statement.bindNull(17);
        } else {
          statement.bindString(17, entity.getDominantHand());
        }
        if (entity.getEyeColor() == null) {
          statement.bindNull(18);
        } else {
          statement.bindString(18, entity.getEyeColor());
        }
        if (entity.getFirstBreath() == null) {
          statement.bindNull(19);
        } else {
          statement.bindString(19, entity.getFirstBreath());
        }
        if (entity.getWeatherConditions() == null) {
          statement.bindNull(20);
        } else {
          statement.bindString(20, entity.getWeatherConditions());
        }
        if (entity.getMoonPhase() == null) {
          statement.bindNull(21);
        } else {
          statement.bindString(21, entity.getMoonPhase());
        }
        if (entity.getHospitalName() == null) {
          statement.bindNull(22);
        } else {
          statement.bindString(22, entity.getHospitalName());
        }
        if (entity.getFirstWord() == null) {
          statement.bindNull(23);
        } else {
          statement.bindString(23, entity.getFirstWord());
        }
        if (entity.getFirstSteps() == null) {
          statement.bindNull(24);
        } else {
          statement.bindString(24, entity.getFirstSteps());
        }
        if (entity.getPreferences() == null) {
          statement.bindNull(25);
        } else {
          statement.bindString(25, entity.getPreferences());
        }
        if (entity.getProfileCompletion() == null) {
          statement.bindNull(26);
        } else {
          statement.bindString(26, entity.getProfileCompletion());
        }
        if (entity.getEnrichmentResult() == null) {
          statement.bindNull(27);
        } else {
          statement.bindString(27, entity.getEnrichmentResult());
        }
        if (entity.getEnrichmentGeneratedAt() == null) {
          statement.bindNull(28);
        } else {
          statement.bindString(28, entity.getEnrichmentGeneratedAt());
        }
        statement.bindLong(29, entity.getUpdatedAt());
        statement.bindString(30, entity.getSyncStatus());
        statement.bindLong(31, entity.getEncryptionVersion());
        statement.bindString(32, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteProfile = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM user_profiles WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllProfiles = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM user_profiles";
        return _query;
      }
    };
    this.__preparedStmtOfMarkProfileAsSynced = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE user_profiles SET syncStatus = 'SYNCED', updatedAt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateProfileCompletion = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "\n"
                + "        UPDATE user_profiles \n"
                + "        SET profileCompletion = ?, updatedAt = ? \n"
                + "        WHERE id = ?\n"
                + "    ";
        return _query;
      }
    };
  }

  @Override
  public Object insertOrUpdateProfile(final UserProfileEntity profile,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfUserProfileEntity.insert(profile);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateProfile(final UserProfileEntity profile,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfUserProfileEntity.handle(profile);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteProfile(final String profileId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteProfile.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, profileId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteProfile.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAllProfiles(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllProfiles.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAllProfiles.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object markProfileAsSynced(final String profileId, final long timestamp,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkProfileAsSynced.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, timestamp);
        _argIndex = 2;
        _stmt.bindString(_argIndex, profileId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfMarkProfileAsSynced.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateProfileCompletion(final String profileId, final String completionJson,
      final long timestamp, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateProfileCompletion.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, completionJson);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, timestamp);
        _argIndex = 3;
        _stmt.bindString(_argIndex, profileId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateProfileCompletion.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<UserProfileEntity> getUserProfileFlow() {
    final String _sql = "SELECT * FROM user_profiles ORDER BY updatedAt DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"user_profiles"}, new Callable<UserProfileEntity>() {
      @Override
      @Nullable
      public UserProfileEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProfileName = CursorUtil.getColumnIndexOrThrow(_cursor, "profileName");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDisplayName = CursorUtil.getColumnIndexOrThrow(_cursor, "displayName");
          final int _cursorIndexOfBirthDateTime = CursorUtil.getColumnIndexOrThrow(_cursor, "birthDateTime");
          final int _cursorIndexOfBirthPlace = CursorUtil.getColumnIndexOrThrow(_cursor, "birthPlace");
          final int _cursorIndexOfMiddleName = CursorUtil.getColumnIndexOrThrow(_cursor, "middleName");
          final int _cursorIndexOfNickname = CursorUtil.getColumnIndexOrThrow(_cursor, "nickname");
          final int _cursorIndexOfSpiritualName = CursorUtil.getColumnIndexOrThrow(_cursor, "spiritualName");
          final int _cursorIndexOfMotherName = CursorUtil.getColumnIndexOrThrow(_cursor, "motherName");
          final int _cursorIndexOfFatherName = CursorUtil.getColumnIndexOrThrow(_cursor, "fatherName");
          final int _cursorIndexOfAncestry = CursorUtil.getColumnIndexOrThrow(_cursor, "ancestry");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfBloodType = CursorUtil.getColumnIndexOrThrow(_cursor, "bloodType");
          final int _cursorIndexOfDominantHand = CursorUtil.getColumnIndexOrThrow(_cursor, "dominantHand");
          final int _cursorIndexOfEyeColor = CursorUtil.getColumnIndexOrThrow(_cursor, "eyeColor");
          final int _cursorIndexOfFirstBreath = CursorUtil.getColumnIndexOrThrow(_cursor, "firstBreath");
          final int _cursorIndexOfWeatherConditions = CursorUtil.getColumnIndexOrThrow(_cursor, "weatherConditions");
          final int _cursorIndexOfMoonPhase = CursorUtil.getColumnIndexOrThrow(_cursor, "moonPhase");
          final int _cursorIndexOfHospitalName = CursorUtil.getColumnIndexOrThrow(_cursor, "hospitalName");
          final int _cursorIndexOfFirstWord = CursorUtil.getColumnIndexOrThrow(_cursor, "firstWord");
          final int _cursorIndexOfFirstSteps = CursorUtil.getColumnIndexOrThrow(_cursor, "firstSteps");
          final int _cursorIndexOfPreferences = CursorUtil.getColumnIndexOrThrow(_cursor, "preferences");
          final int _cursorIndexOfProfileCompletion = CursorUtil.getColumnIndexOrThrow(_cursor, "profileCompletion");
          final int _cursorIndexOfEnrichmentResult = CursorUtil.getColumnIndexOrThrow(_cursor, "enrichmentResult");
          final int _cursorIndexOfEnrichmentGeneratedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "enrichmentGeneratedAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfEncryptionVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "encryptionVersion");
          final UserProfileEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpProfileName;
            _tmpProfileName = _cursor.getString(_cursorIndexOfProfileName);
            final String _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getString(_cursorIndexOfCreatedAt);
            final String _tmpLastModified;
            _tmpLastModified = _cursor.getString(_cursorIndexOfLastModified);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpDisplayName;
            if (_cursor.isNull(_cursorIndexOfDisplayName)) {
              _tmpDisplayName = null;
            } else {
              _tmpDisplayName = _cursor.getString(_cursorIndexOfDisplayName);
            }
            final String _tmpBirthDateTime;
            if (_cursor.isNull(_cursorIndexOfBirthDateTime)) {
              _tmpBirthDateTime = null;
            } else {
              _tmpBirthDateTime = _cursor.getString(_cursorIndexOfBirthDateTime);
            }
            final String _tmpBirthPlace;
            if (_cursor.isNull(_cursorIndexOfBirthPlace)) {
              _tmpBirthPlace = null;
            } else {
              _tmpBirthPlace = _cursor.getString(_cursorIndexOfBirthPlace);
            }
            final String _tmpMiddleName;
            if (_cursor.isNull(_cursorIndexOfMiddleName)) {
              _tmpMiddleName = null;
            } else {
              _tmpMiddleName = _cursor.getString(_cursorIndexOfMiddleName);
            }
            final String _tmpNickname;
            if (_cursor.isNull(_cursorIndexOfNickname)) {
              _tmpNickname = null;
            } else {
              _tmpNickname = _cursor.getString(_cursorIndexOfNickname);
            }
            final String _tmpSpiritualName;
            if (_cursor.isNull(_cursorIndexOfSpiritualName)) {
              _tmpSpiritualName = null;
            } else {
              _tmpSpiritualName = _cursor.getString(_cursorIndexOfSpiritualName);
            }
            final String _tmpMotherName;
            if (_cursor.isNull(_cursorIndexOfMotherName)) {
              _tmpMotherName = null;
            } else {
              _tmpMotherName = _cursor.getString(_cursorIndexOfMotherName);
            }
            final String _tmpFatherName;
            if (_cursor.isNull(_cursorIndexOfFatherName)) {
              _tmpFatherName = null;
            } else {
              _tmpFatherName = _cursor.getString(_cursorIndexOfFatherName);
            }
            final String _tmpAncestry;
            if (_cursor.isNull(_cursorIndexOfAncestry)) {
              _tmpAncestry = null;
            } else {
              _tmpAncestry = _cursor.getString(_cursorIndexOfAncestry);
            }
            final String _tmpGender;
            if (_cursor.isNull(_cursorIndexOfGender)) {
              _tmpGender = null;
            } else {
              _tmpGender = _cursor.getString(_cursorIndexOfGender);
            }
            final String _tmpBloodType;
            if (_cursor.isNull(_cursorIndexOfBloodType)) {
              _tmpBloodType = null;
            } else {
              _tmpBloodType = _cursor.getString(_cursorIndexOfBloodType);
            }
            final String _tmpDominantHand;
            if (_cursor.isNull(_cursorIndexOfDominantHand)) {
              _tmpDominantHand = null;
            } else {
              _tmpDominantHand = _cursor.getString(_cursorIndexOfDominantHand);
            }
            final String _tmpEyeColor;
            if (_cursor.isNull(_cursorIndexOfEyeColor)) {
              _tmpEyeColor = null;
            } else {
              _tmpEyeColor = _cursor.getString(_cursorIndexOfEyeColor);
            }
            final String _tmpFirstBreath;
            if (_cursor.isNull(_cursorIndexOfFirstBreath)) {
              _tmpFirstBreath = null;
            } else {
              _tmpFirstBreath = _cursor.getString(_cursorIndexOfFirstBreath);
            }
            final String _tmpWeatherConditions;
            if (_cursor.isNull(_cursorIndexOfWeatherConditions)) {
              _tmpWeatherConditions = null;
            } else {
              _tmpWeatherConditions = _cursor.getString(_cursorIndexOfWeatherConditions);
            }
            final String _tmpMoonPhase;
            if (_cursor.isNull(_cursorIndexOfMoonPhase)) {
              _tmpMoonPhase = null;
            } else {
              _tmpMoonPhase = _cursor.getString(_cursorIndexOfMoonPhase);
            }
            final String _tmpHospitalName;
            if (_cursor.isNull(_cursorIndexOfHospitalName)) {
              _tmpHospitalName = null;
            } else {
              _tmpHospitalName = _cursor.getString(_cursorIndexOfHospitalName);
            }
            final String _tmpFirstWord;
            if (_cursor.isNull(_cursorIndexOfFirstWord)) {
              _tmpFirstWord = null;
            } else {
              _tmpFirstWord = _cursor.getString(_cursorIndexOfFirstWord);
            }
            final String _tmpFirstSteps;
            if (_cursor.isNull(_cursorIndexOfFirstSteps)) {
              _tmpFirstSteps = null;
            } else {
              _tmpFirstSteps = _cursor.getString(_cursorIndexOfFirstSteps);
            }
            final String _tmpPreferences;
            if (_cursor.isNull(_cursorIndexOfPreferences)) {
              _tmpPreferences = null;
            } else {
              _tmpPreferences = _cursor.getString(_cursorIndexOfPreferences);
            }
            final String _tmpProfileCompletion;
            if (_cursor.isNull(_cursorIndexOfProfileCompletion)) {
              _tmpProfileCompletion = null;
            } else {
              _tmpProfileCompletion = _cursor.getString(_cursorIndexOfProfileCompletion);
            }
            final String _tmpEnrichmentResult;
            if (_cursor.isNull(_cursorIndexOfEnrichmentResult)) {
              _tmpEnrichmentResult = null;
            } else {
              _tmpEnrichmentResult = _cursor.getString(_cursorIndexOfEnrichmentResult);
            }
            final String _tmpEnrichmentGeneratedAt;
            if (_cursor.isNull(_cursorIndexOfEnrichmentGeneratedAt)) {
              _tmpEnrichmentGeneratedAt = null;
            } else {
              _tmpEnrichmentGeneratedAt = _cursor.getString(_cursorIndexOfEnrichmentGeneratedAt);
            }
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final String _tmpSyncStatus;
            _tmpSyncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
            final int _tmpEncryptionVersion;
            _tmpEncryptionVersion = _cursor.getInt(_cursorIndexOfEncryptionVersion);
            _result = new UserProfileEntity(_tmpId,_tmpProfileName,_tmpCreatedAt,_tmpLastModified,_tmpName,_tmpDisplayName,_tmpBirthDateTime,_tmpBirthPlace,_tmpMiddleName,_tmpNickname,_tmpSpiritualName,_tmpMotherName,_tmpFatherName,_tmpAncestry,_tmpGender,_tmpBloodType,_tmpDominantHand,_tmpEyeColor,_tmpFirstBreath,_tmpWeatherConditions,_tmpMoonPhase,_tmpHospitalName,_tmpFirstWord,_tmpFirstSteps,_tmpPreferences,_tmpProfileCompletion,_tmpEnrichmentResult,_tmpEnrichmentGeneratedAt,_tmpUpdatedAt,_tmpSyncStatus,_tmpEncryptionVersion);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getUserProfile(final Continuation<? super UserProfileEntity> $completion) {
    final String _sql = "SELECT * FROM user_profiles ORDER BY updatedAt DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<UserProfileEntity>() {
      @Override
      @Nullable
      public UserProfileEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProfileName = CursorUtil.getColumnIndexOrThrow(_cursor, "profileName");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDisplayName = CursorUtil.getColumnIndexOrThrow(_cursor, "displayName");
          final int _cursorIndexOfBirthDateTime = CursorUtil.getColumnIndexOrThrow(_cursor, "birthDateTime");
          final int _cursorIndexOfBirthPlace = CursorUtil.getColumnIndexOrThrow(_cursor, "birthPlace");
          final int _cursorIndexOfMiddleName = CursorUtil.getColumnIndexOrThrow(_cursor, "middleName");
          final int _cursorIndexOfNickname = CursorUtil.getColumnIndexOrThrow(_cursor, "nickname");
          final int _cursorIndexOfSpiritualName = CursorUtil.getColumnIndexOrThrow(_cursor, "spiritualName");
          final int _cursorIndexOfMotherName = CursorUtil.getColumnIndexOrThrow(_cursor, "motherName");
          final int _cursorIndexOfFatherName = CursorUtil.getColumnIndexOrThrow(_cursor, "fatherName");
          final int _cursorIndexOfAncestry = CursorUtil.getColumnIndexOrThrow(_cursor, "ancestry");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfBloodType = CursorUtil.getColumnIndexOrThrow(_cursor, "bloodType");
          final int _cursorIndexOfDominantHand = CursorUtil.getColumnIndexOrThrow(_cursor, "dominantHand");
          final int _cursorIndexOfEyeColor = CursorUtil.getColumnIndexOrThrow(_cursor, "eyeColor");
          final int _cursorIndexOfFirstBreath = CursorUtil.getColumnIndexOrThrow(_cursor, "firstBreath");
          final int _cursorIndexOfWeatherConditions = CursorUtil.getColumnIndexOrThrow(_cursor, "weatherConditions");
          final int _cursorIndexOfMoonPhase = CursorUtil.getColumnIndexOrThrow(_cursor, "moonPhase");
          final int _cursorIndexOfHospitalName = CursorUtil.getColumnIndexOrThrow(_cursor, "hospitalName");
          final int _cursorIndexOfFirstWord = CursorUtil.getColumnIndexOrThrow(_cursor, "firstWord");
          final int _cursorIndexOfFirstSteps = CursorUtil.getColumnIndexOrThrow(_cursor, "firstSteps");
          final int _cursorIndexOfPreferences = CursorUtil.getColumnIndexOrThrow(_cursor, "preferences");
          final int _cursorIndexOfProfileCompletion = CursorUtil.getColumnIndexOrThrow(_cursor, "profileCompletion");
          final int _cursorIndexOfEnrichmentResult = CursorUtil.getColumnIndexOrThrow(_cursor, "enrichmentResult");
          final int _cursorIndexOfEnrichmentGeneratedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "enrichmentGeneratedAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfEncryptionVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "encryptionVersion");
          final UserProfileEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpProfileName;
            _tmpProfileName = _cursor.getString(_cursorIndexOfProfileName);
            final String _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getString(_cursorIndexOfCreatedAt);
            final String _tmpLastModified;
            _tmpLastModified = _cursor.getString(_cursorIndexOfLastModified);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpDisplayName;
            if (_cursor.isNull(_cursorIndexOfDisplayName)) {
              _tmpDisplayName = null;
            } else {
              _tmpDisplayName = _cursor.getString(_cursorIndexOfDisplayName);
            }
            final String _tmpBirthDateTime;
            if (_cursor.isNull(_cursorIndexOfBirthDateTime)) {
              _tmpBirthDateTime = null;
            } else {
              _tmpBirthDateTime = _cursor.getString(_cursorIndexOfBirthDateTime);
            }
            final String _tmpBirthPlace;
            if (_cursor.isNull(_cursorIndexOfBirthPlace)) {
              _tmpBirthPlace = null;
            } else {
              _tmpBirthPlace = _cursor.getString(_cursorIndexOfBirthPlace);
            }
            final String _tmpMiddleName;
            if (_cursor.isNull(_cursorIndexOfMiddleName)) {
              _tmpMiddleName = null;
            } else {
              _tmpMiddleName = _cursor.getString(_cursorIndexOfMiddleName);
            }
            final String _tmpNickname;
            if (_cursor.isNull(_cursorIndexOfNickname)) {
              _tmpNickname = null;
            } else {
              _tmpNickname = _cursor.getString(_cursorIndexOfNickname);
            }
            final String _tmpSpiritualName;
            if (_cursor.isNull(_cursorIndexOfSpiritualName)) {
              _tmpSpiritualName = null;
            } else {
              _tmpSpiritualName = _cursor.getString(_cursorIndexOfSpiritualName);
            }
            final String _tmpMotherName;
            if (_cursor.isNull(_cursorIndexOfMotherName)) {
              _tmpMotherName = null;
            } else {
              _tmpMotherName = _cursor.getString(_cursorIndexOfMotherName);
            }
            final String _tmpFatherName;
            if (_cursor.isNull(_cursorIndexOfFatherName)) {
              _tmpFatherName = null;
            } else {
              _tmpFatherName = _cursor.getString(_cursorIndexOfFatherName);
            }
            final String _tmpAncestry;
            if (_cursor.isNull(_cursorIndexOfAncestry)) {
              _tmpAncestry = null;
            } else {
              _tmpAncestry = _cursor.getString(_cursorIndexOfAncestry);
            }
            final String _tmpGender;
            if (_cursor.isNull(_cursorIndexOfGender)) {
              _tmpGender = null;
            } else {
              _tmpGender = _cursor.getString(_cursorIndexOfGender);
            }
            final String _tmpBloodType;
            if (_cursor.isNull(_cursorIndexOfBloodType)) {
              _tmpBloodType = null;
            } else {
              _tmpBloodType = _cursor.getString(_cursorIndexOfBloodType);
            }
            final String _tmpDominantHand;
            if (_cursor.isNull(_cursorIndexOfDominantHand)) {
              _tmpDominantHand = null;
            } else {
              _tmpDominantHand = _cursor.getString(_cursorIndexOfDominantHand);
            }
            final String _tmpEyeColor;
            if (_cursor.isNull(_cursorIndexOfEyeColor)) {
              _tmpEyeColor = null;
            } else {
              _tmpEyeColor = _cursor.getString(_cursorIndexOfEyeColor);
            }
            final String _tmpFirstBreath;
            if (_cursor.isNull(_cursorIndexOfFirstBreath)) {
              _tmpFirstBreath = null;
            } else {
              _tmpFirstBreath = _cursor.getString(_cursorIndexOfFirstBreath);
            }
            final String _tmpWeatherConditions;
            if (_cursor.isNull(_cursorIndexOfWeatherConditions)) {
              _tmpWeatherConditions = null;
            } else {
              _tmpWeatherConditions = _cursor.getString(_cursorIndexOfWeatherConditions);
            }
            final String _tmpMoonPhase;
            if (_cursor.isNull(_cursorIndexOfMoonPhase)) {
              _tmpMoonPhase = null;
            } else {
              _tmpMoonPhase = _cursor.getString(_cursorIndexOfMoonPhase);
            }
            final String _tmpHospitalName;
            if (_cursor.isNull(_cursorIndexOfHospitalName)) {
              _tmpHospitalName = null;
            } else {
              _tmpHospitalName = _cursor.getString(_cursorIndexOfHospitalName);
            }
            final String _tmpFirstWord;
            if (_cursor.isNull(_cursorIndexOfFirstWord)) {
              _tmpFirstWord = null;
            } else {
              _tmpFirstWord = _cursor.getString(_cursorIndexOfFirstWord);
            }
            final String _tmpFirstSteps;
            if (_cursor.isNull(_cursorIndexOfFirstSteps)) {
              _tmpFirstSteps = null;
            } else {
              _tmpFirstSteps = _cursor.getString(_cursorIndexOfFirstSteps);
            }
            final String _tmpPreferences;
            if (_cursor.isNull(_cursorIndexOfPreferences)) {
              _tmpPreferences = null;
            } else {
              _tmpPreferences = _cursor.getString(_cursorIndexOfPreferences);
            }
            final String _tmpProfileCompletion;
            if (_cursor.isNull(_cursorIndexOfProfileCompletion)) {
              _tmpProfileCompletion = null;
            } else {
              _tmpProfileCompletion = _cursor.getString(_cursorIndexOfProfileCompletion);
            }
            final String _tmpEnrichmentResult;
            if (_cursor.isNull(_cursorIndexOfEnrichmentResult)) {
              _tmpEnrichmentResult = null;
            } else {
              _tmpEnrichmentResult = _cursor.getString(_cursorIndexOfEnrichmentResult);
            }
            final String _tmpEnrichmentGeneratedAt;
            if (_cursor.isNull(_cursorIndexOfEnrichmentGeneratedAt)) {
              _tmpEnrichmentGeneratedAt = null;
            } else {
              _tmpEnrichmentGeneratedAt = _cursor.getString(_cursorIndexOfEnrichmentGeneratedAt);
            }
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final String _tmpSyncStatus;
            _tmpSyncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
            final int _tmpEncryptionVersion;
            _tmpEncryptionVersion = _cursor.getInt(_cursorIndexOfEncryptionVersion);
            _result = new UserProfileEntity(_tmpId,_tmpProfileName,_tmpCreatedAt,_tmpLastModified,_tmpName,_tmpDisplayName,_tmpBirthDateTime,_tmpBirthPlace,_tmpMiddleName,_tmpNickname,_tmpSpiritualName,_tmpMotherName,_tmpFatherName,_tmpAncestry,_tmpGender,_tmpBloodType,_tmpDominantHand,_tmpEyeColor,_tmpFirstBreath,_tmpWeatherConditions,_tmpMoonPhase,_tmpHospitalName,_tmpFirstWord,_tmpFirstSteps,_tmpPreferences,_tmpProfileCompletion,_tmpEnrichmentResult,_tmpEnrichmentGeneratedAt,_tmpUpdatedAt,_tmpSyncStatus,_tmpEncryptionVersion);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getUserProfileById(final String profileId,
      final Continuation<? super UserProfileEntity> $completion) {
    final String _sql = "SELECT * FROM user_profiles WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, profileId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<UserProfileEntity>() {
      @Override
      @Nullable
      public UserProfileEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProfileName = CursorUtil.getColumnIndexOrThrow(_cursor, "profileName");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDisplayName = CursorUtil.getColumnIndexOrThrow(_cursor, "displayName");
          final int _cursorIndexOfBirthDateTime = CursorUtil.getColumnIndexOrThrow(_cursor, "birthDateTime");
          final int _cursorIndexOfBirthPlace = CursorUtil.getColumnIndexOrThrow(_cursor, "birthPlace");
          final int _cursorIndexOfMiddleName = CursorUtil.getColumnIndexOrThrow(_cursor, "middleName");
          final int _cursorIndexOfNickname = CursorUtil.getColumnIndexOrThrow(_cursor, "nickname");
          final int _cursorIndexOfSpiritualName = CursorUtil.getColumnIndexOrThrow(_cursor, "spiritualName");
          final int _cursorIndexOfMotherName = CursorUtil.getColumnIndexOrThrow(_cursor, "motherName");
          final int _cursorIndexOfFatherName = CursorUtil.getColumnIndexOrThrow(_cursor, "fatherName");
          final int _cursorIndexOfAncestry = CursorUtil.getColumnIndexOrThrow(_cursor, "ancestry");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfBloodType = CursorUtil.getColumnIndexOrThrow(_cursor, "bloodType");
          final int _cursorIndexOfDominantHand = CursorUtil.getColumnIndexOrThrow(_cursor, "dominantHand");
          final int _cursorIndexOfEyeColor = CursorUtil.getColumnIndexOrThrow(_cursor, "eyeColor");
          final int _cursorIndexOfFirstBreath = CursorUtil.getColumnIndexOrThrow(_cursor, "firstBreath");
          final int _cursorIndexOfWeatherConditions = CursorUtil.getColumnIndexOrThrow(_cursor, "weatherConditions");
          final int _cursorIndexOfMoonPhase = CursorUtil.getColumnIndexOrThrow(_cursor, "moonPhase");
          final int _cursorIndexOfHospitalName = CursorUtil.getColumnIndexOrThrow(_cursor, "hospitalName");
          final int _cursorIndexOfFirstWord = CursorUtil.getColumnIndexOrThrow(_cursor, "firstWord");
          final int _cursorIndexOfFirstSteps = CursorUtil.getColumnIndexOrThrow(_cursor, "firstSteps");
          final int _cursorIndexOfPreferences = CursorUtil.getColumnIndexOrThrow(_cursor, "preferences");
          final int _cursorIndexOfProfileCompletion = CursorUtil.getColumnIndexOrThrow(_cursor, "profileCompletion");
          final int _cursorIndexOfEnrichmentResult = CursorUtil.getColumnIndexOrThrow(_cursor, "enrichmentResult");
          final int _cursorIndexOfEnrichmentGeneratedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "enrichmentGeneratedAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfEncryptionVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "encryptionVersion");
          final UserProfileEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpProfileName;
            _tmpProfileName = _cursor.getString(_cursorIndexOfProfileName);
            final String _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getString(_cursorIndexOfCreatedAt);
            final String _tmpLastModified;
            _tmpLastModified = _cursor.getString(_cursorIndexOfLastModified);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpDisplayName;
            if (_cursor.isNull(_cursorIndexOfDisplayName)) {
              _tmpDisplayName = null;
            } else {
              _tmpDisplayName = _cursor.getString(_cursorIndexOfDisplayName);
            }
            final String _tmpBirthDateTime;
            if (_cursor.isNull(_cursorIndexOfBirthDateTime)) {
              _tmpBirthDateTime = null;
            } else {
              _tmpBirthDateTime = _cursor.getString(_cursorIndexOfBirthDateTime);
            }
            final String _tmpBirthPlace;
            if (_cursor.isNull(_cursorIndexOfBirthPlace)) {
              _tmpBirthPlace = null;
            } else {
              _tmpBirthPlace = _cursor.getString(_cursorIndexOfBirthPlace);
            }
            final String _tmpMiddleName;
            if (_cursor.isNull(_cursorIndexOfMiddleName)) {
              _tmpMiddleName = null;
            } else {
              _tmpMiddleName = _cursor.getString(_cursorIndexOfMiddleName);
            }
            final String _tmpNickname;
            if (_cursor.isNull(_cursorIndexOfNickname)) {
              _tmpNickname = null;
            } else {
              _tmpNickname = _cursor.getString(_cursorIndexOfNickname);
            }
            final String _tmpSpiritualName;
            if (_cursor.isNull(_cursorIndexOfSpiritualName)) {
              _tmpSpiritualName = null;
            } else {
              _tmpSpiritualName = _cursor.getString(_cursorIndexOfSpiritualName);
            }
            final String _tmpMotherName;
            if (_cursor.isNull(_cursorIndexOfMotherName)) {
              _tmpMotherName = null;
            } else {
              _tmpMotherName = _cursor.getString(_cursorIndexOfMotherName);
            }
            final String _tmpFatherName;
            if (_cursor.isNull(_cursorIndexOfFatherName)) {
              _tmpFatherName = null;
            } else {
              _tmpFatherName = _cursor.getString(_cursorIndexOfFatherName);
            }
            final String _tmpAncestry;
            if (_cursor.isNull(_cursorIndexOfAncestry)) {
              _tmpAncestry = null;
            } else {
              _tmpAncestry = _cursor.getString(_cursorIndexOfAncestry);
            }
            final String _tmpGender;
            if (_cursor.isNull(_cursorIndexOfGender)) {
              _tmpGender = null;
            } else {
              _tmpGender = _cursor.getString(_cursorIndexOfGender);
            }
            final String _tmpBloodType;
            if (_cursor.isNull(_cursorIndexOfBloodType)) {
              _tmpBloodType = null;
            } else {
              _tmpBloodType = _cursor.getString(_cursorIndexOfBloodType);
            }
            final String _tmpDominantHand;
            if (_cursor.isNull(_cursorIndexOfDominantHand)) {
              _tmpDominantHand = null;
            } else {
              _tmpDominantHand = _cursor.getString(_cursorIndexOfDominantHand);
            }
            final String _tmpEyeColor;
            if (_cursor.isNull(_cursorIndexOfEyeColor)) {
              _tmpEyeColor = null;
            } else {
              _tmpEyeColor = _cursor.getString(_cursorIndexOfEyeColor);
            }
            final String _tmpFirstBreath;
            if (_cursor.isNull(_cursorIndexOfFirstBreath)) {
              _tmpFirstBreath = null;
            } else {
              _tmpFirstBreath = _cursor.getString(_cursorIndexOfFirstBreath);
            }
            final String _tmpWeatherConditions;
            if (_cursor.isNull(_cursorIndexOfWeatherConditions)) {
              _tmpWeatherConditions = null;
            } else {
              _tmpWeatherConditions = _cursor.getString(_cursorIndexOfWeatherConditions);
            }
            final String _tmpMoonPhase;
            if (_cursor.isNull(_cursorIndexOfMoonPhase)) {
              _tmpMoonPhase = null;
            } else {
              _tmpMoonPhase = _cursor.getString(_cursorIndexOfMoonPhase);
            }
            final String _tmpHospitalName;
            if (_cursor.isNull(_cursorIndexOfHospitalName)) {
              _tmpHospitalName = null;
            } else {
              _tmpHospitalName = _cursor.getString(_cursorIndexOfHospitalName);
            }
            final String _tmpFirstWord;
            if (_cursor.isNull(_cursorIndexOfFirstWord)) {
              _tmpFirstWord = null;
            } else {
              _tmpFirstWord = _cursor.getString(_cursorIndexOfFirstWord);
            }
            final String _tmpFirstSteps;
            if (_cursor.isNull(_cursorIndexOfFirstSteps)) {
              _tmpFirstSteps = null;
            } else {
              _tmpFirstSteps = _cursor.getString(_cursorIndexOfFirstSteps);
            }
            final String _tmpPreferences;
            if (_cursor.isNull(_cursorIndexOfPreferences)) {
              _tmpPreferences = null;
            } else {
              _tmpPreferences = _cursor.getString(_cursorIndexOfPreferences);
            }
            final String _tmpProfileCompletion;
            if (_cursor.isNull(_cursorIndexOfProfileCompletion)) {
              _tmpProfileCompletion = null;
            } else {
              _tmpProfileCompletion = _cursor.getString(_cursorIndexOfProfileCompletion);
            }
            final String _tmpEnrichmentResult;
            if (_cursor.isNull(_cursorIndexOfEnrichmentResult)) {
              _tmpEnrichmentResult = null;
            } else {
              _tmpEnrichmentResult = _cursor.getString(_cursorIndexOfEnrichmentResult);
            }
            final String _tmpEnrichmentGeneratedAt;
            if (_cursor.isNull(_cursorIndexOfEnrichmentGeneratedAt)) {
              _tmpEnrichmentGeneratedAt = null;
            } else {
              _tmpEnrichmentGeneratedAt = _cursor.getString(_cursorIndexOfEnrichmentGeneratedAt);
            }
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final String _tmpSyncStatus;
            _tmpSyncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
            final int _tmpEncryptionVersion;
            _tmpEncryptionVersion = _cursor.getInt(_cursorIndexOfEncryptionVersion);
            _result = new UserProfileEntity(_tmpId,_tmpProfileName,_tmpCreatedAt,_tmpLastModified,_tmpName,_tmpDisplayName,_tmpBirthDateTime,_tmpBirthPlace,_tmpMiddleName,_tmpNickname,_tmpSpiritualName,_tmpMotherName,_tmpFatherName,_tmpAncestry,_tmpGender,_tmpBloodType,_tmpDominantHand,_tmpEyeColor,_tmpFirstBreath,_tmpWeatherConditions,_tmpMoonPhase,_tmpHospitalName,_tmpFirstWord,_tmpFirstSteps,_tmpPreferences,_tmpProfileCompletion,_tmpEnrichmentResult,_tmpEnrichmentGeneratedAt,_tmpUpdatedAt,_tmpSyncStatus,_tmpEncryptionVersion);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<UserProfileEntity>> getAllProfilesFlow() {
    final String _sql = "SELECT * FROM user_profiles ORDER BY updatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"user_profiles"}, new Callable<List<UserProfileEntity>>() {
      @Override
      @NonNull
      public List<UserProfileEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProfileName = CursorUtil.getColumnIndexOrThrow(_cursor, "profileName");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDisplayName = CursorUtil.getColumnIndexOrThrow(_cursor, "displayName");
          final int _cursorIndexOfBirthDateTime = CursorUtil.getColumnIndexOrThrow(_cursor, "birthDateTime");
          final int _cursorIndexOfBirthPlace = CursorUtil.getColumnIndexOrThrow(_cursor, "birthPlace");
          final int _cursorIndexOfMiddleName = CursorUtil.getColumnIndexOrThrow(_cursor, "middleName");
          final int _cursorIndexOfNickname = CursorUtil.getColumnIndexOrThrow(_cursor, "nickname");
          final int _cursorIndexOfSpiritualName = CursorUtil.getColumnIndexOrThrow(_cursor, "spiritualName");
          final int _cursorIndexOfMotherName = CursorUtil.getColumnIndexOrThrow(_cursor, "motherName");
          final int _cursorIndexOfFatherName = CursorUtil.getColumnIndexOrThrow(_cursor, "fatherName");
          final int _cursorIndexOfAncestry = CursorUtil.getColumnIndexOrThrow(_cursor, "ancestry");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfBloodType = CursorUtil.getColumnIndexOrThrow(_cursor, "bloodType");
          final int _cursorIndexOfDominantHand = CursorUtil.getColumnIndexOrThrow(_cursor, "dominantHand");
          final int _cursorIndexOfEyeColor = CursorUtil.getColumnIndexOrThrow(_cursor, "eyeColor");
          final int _cursorIndexOfFirstBreath = CursorUtil.getColumnIndexOrThrow(_cursor, "firstBreath");
          final int _cursorIndexOfWeatherConditions = CursorUtil.getColumnIndexOrThrow(_cursor, "weatherConditions");
          final int _cursorIndexOfMoonPhase = CursorUtil.getColumnIndexOrThrow(_cursor, "moonPhase");
          final int _cursorIndexOfHospitalName = CursorUtil.getColumnIndexOrThrow(_cursor, "hospitalName");
          final int _cursorIndexOfFirstWord = CursorUtil.getColumnIndexOrThrow(_cursor, "firstWord");
          final int _cursorIndexOfFirstSteps = CursorUtil.getColumnIndexOrThrow(_cursor, "firstSteps");
          final int _cursorIndexOfPreferences = CursorUtil.getColumnIndexOrThrow(_cursor, "preferences");
          final int _cursorIndexOfProfileCompletion = CursorUtil.getColumnIndexOrThrow(_cursor, "profileCompletion");
          final int _cursorIndexOfEnrichmentResult = CursorUtil.getColumnIndexOrThrow(_cursor, "enrichmentResult");
          final int _cursorIndexOfEnrichmentGeneratedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "enrichmentGeneratedAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfEncryptionVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "encryptionVersion");
          final List<UserProfileEntity> _result = new ArrayList<UserProfileEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final UserProfileEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpProfileName;
            _tmpProfileName = _cursor.getString(_cursorIndexOfProfileName);
            final String _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getString(_cursorIndexOfCreatedAt);
            final String _tmpLastModified;
            _tmpLastModified = _cursor.getString(_cursorIndexOfLastModified);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpDisplayName;
            if (_cursor.isNull(_cursorIndexOfDisplayName)) {
              _tmpDisplayName = null;
            } else {
              _tmpDisplayName = _cursor.getString(_cursorIndexOfDisplayName);
            }
            final String _tmpBirthDateTime;
            if (_cursor.isNull(_cursorIndexOfBirthDateTime)) {
              _tmpBirthDateTime = null;
            } else {
              _tmpBirthDateTime = _cursor.getString(_cursorIndexOfBirthDateTime);
            }
            final String _tmpBirthPlace;
            if (_cursor.isNull(_cursorIndexOfBirthPlace)) {
              _tmpBirthPlace = null;
            } else {
              _tmpBirthPlace = _cursor.getString(_cursorIndexOfBirthPlace);
            }
            final String _tmpMiddleName;
            if (_cursor.isNull(_cursorIndexOfMiddleName)) {
              _tmpMiddleName = null;
            } else {
              _tmpMiddleName = _cursor.getString(_cursorIndexOfMiddleName);
            }
            final String _tmpNickname;
            if (_cursor.isNull(_cursorIndexOfNickname)) {
              _tmpNickname = null;
            } else {
              _tmpNickname = _cursor.getString(_cursorIndexOfNickname);
            }
            final String _tmpSpiritualName;
            if (_cursor.isNull(_cursorIndexOfSpiritualName)) {
              _tmpSpiritualName = null;
            } else {
              _tmpSpiritualName = _cursor.getString(_cursorIndexOfSpiritualName);
            }
            final String _tmpMotherName;
            if (_cursor.isNull(_cursorIndexOfMotherName)) {
              _tmpMotherName = null;
            } else {
              _tmpMotherName = _cursor.getString(_cursorIndexOfMotherName);
            }
            final String _tmpFatherName;
            if (_cursor.isNull(_cursorIndexOfFatherName)) {
              _tmpFatherName = null;
            } else {
              _tmpFatherName = _cursor.getString(_cursorIndexOfFatherName);
            }
            final String _tmpAncestry;
            if (_cursor.isNull(_cursorIndexOfAncestry)) {
              _tmpAncestry = null;
            } else {
              _tmpAncestry = _cursor.getString(_cursorIndexOfAncestry);
            }
            final String _tmpGender;
            if (_cursor.isNull(_cursorIndexOfGender)) {
              _tmpGender = null;
            } else {
              _tmpGender = _cursor.getString(_cursorIndexOfGender);
            }
            final String _tmpBloodType;
            if (_cursor.isNull(_cursorIndexOfBloodType)) {
              _tmpBloodType = null;
            } else {
              _tmpBloodType = _cursor.getString(_cursorIndexOfBloodType);
            }
            final String _tmpDominantHand;
            if (_cursor.isNull(_cursorIndexOfDominantHand)) {
              _tmpDominantHand = null;
            } else {
              _tmpDominantHand = _cursor.getString(_cursorIndexOfDominantHand);
            }
            final String _tmpEyeColor;
            if (_cursor.isNull(_cursorIndexOfEyeColor)) {
              _tmpEyeColor = null;
            } else {
              _tmpEyeColor = _cursor.getString(_cursorIndexOfEyeColor);
            }
            final String _tmpFirstBreath;
            if (_cursor.isNull(_cursorIndexOfFirstBreath)) {
              _tmpFirstBreath = null;
            } else {
              _tmpFirstBreath = _cursor.getString(_cursorIndexOfFirstBreath);
            }
            final String _tmpWeatherConditions;
            if (_cursor.isNull(_cursorIndexOfWeatherConditions)) {
              _tmpWeatherConditions = null;
            } else {
              _tmpWeatherConditions = _cursor.getString(_cursorIndexOfWeatherConditions);
            }
            final String _tmpMoonPhase;
            if (_cursor.isNull(_cursorIndexOfMoonPhase)) {
              _tmpMoonPhase = null;
            } else {
              _tmpMoonPhase = _cursor.getString(_cursorIndexOfMoonPhase);
            }
            final String _tmpHospitalName;
            if (_cursor.isNull(_cursorIndexOfHospitalName)) {
              _tmpHospitalName = null;
            } else {
              _tmpHospitalName = _cursor.getString(_cursorIndexOfHospitalName);
            }
            final String _tmpFirstWord;
            if (_cursor.isNull(_cursorIndexOfFirstWord)) {
              _tmpFirstWord = null;
            } else {
              _tmpFirstWord = _cursor.getString(_cursorIndexOfFirstWord);
            }
            final String _tmpFirstSteps;
            if (_cursor.isNull(_cursorIndexOfFirstSteps)) {
              _tmpFirstSteps = null;
            } else {
              _tmpFirstSteps = _cursor.getString(_cursorIndexOfFirstSteps);
            }
            final String _tmpPreferences;
            if (_cursor.isNull(_cursorIndexOfPreferences)) {
              _tmpPreferences = null;
            } else {
              _tmpPreferences = _cursor.getString(_cursorIndexOfPreferences);
            }
            final String _tmpProfileCompletion;
            if (_cursor.isNull(_cursorIndexOfProfileCompletion)) {
              _tmpProfileCompletion = null;
            } else {
              _tmpProfileCompletion = _cursor.getString(_cursorIndexOfProfileCompletion);
            }
            final String _tmpEnrichmentResult;
            if (_cursor.isNull(_cursorIndexOfEnrichmentResult)) {
              _tmpEnrichmentResult = null;
            } else {
              _tmpEnrichmentResult = _cursor.getString(_cursorIndexOfEnrichmentResult);
            }
            final String _tmpEnrichmentGeneratedAt;
            if (_cursor.isNull(_cursorIndexOfEnrichmentGeneratedAt)) {
              _tmpEnrichmentGeneratedAt = null;
            } else {
              _tmpEnrichmentGeneratedAt = _cursor.getString(_cursorIndexOfEnrichmentGeneratedAt);
            }
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final String _tmpSyncStatus;
            _tmpSyncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
            final int _tmpEncryptionVersion;
            _tmpEncryptionVersion = _cursor.getInt(_cursorIndexOfEncryptionVersion);
            _item = new UserProfileEntity(_tmpId,_tmpProfileName,_tmpCreatedAt,_tmpLastModified,_tmpName,_tmpDisplayName,_tmpBirthDateTime,_tmpBirthPlace,_tmpMiddleName,_tmpNickname,_tmpSpiritualName,_tmpMotherName,_tmpFatherName,_tmpAncestry,_tmpGender,_tmpBloodType,_tmpDominantHand,_tmpEyeColor,_tmpFirstBreath,_tmpWeatherConditions,_tmpMoonPhase,_tmpHospitalName,_tmpFirstWord,_tmpFirstSteps,_tmpPreferences,_tmpProfileCompletion,_tmpEnrichmentResult,_tmpEnrichmentGeneratedAt,_tmpUpdatedAt,_tmpSyncStatus,_tmpEncryptionVersion);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getAllProfiles(final Continuation<? super List<UserProfileEntity>> $completion) {
    final String _sql = "SELECT * FROM user_profiles ORDER BY updatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<UserProfileEntity>>() {
      @Override
      @NonNull
      public List<UserProfileEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProfileName = CursorUtil.getColumnIndexOrThrow(_cursor, "profileName");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDisplayName = CursorUtil.getColumnIndexOrThrow(_cursor, "displayName");
          final int _cursorIndexOfBirthDateTime = CursorUtil.getColumnIndexOrThrow(_cursor, "birthDateTime");
          final int _cursorIndexOfBirthPlace = CursorUtil.getColumnIndexOrThrow(_cursor, "birthPlace");
          final int _cursorIndexOfMiddleName = CursorUtil.getColumnIndexOrThrow(_cursor, "middleName");
          final int _cursorIndexOfNickname = CursorUtil.getColumnIndexOrThrow(_cursor, "nickname");
          final int _cursorIndexOfSpiritualName = CursorUtil.getColumnIndexOrThrow(_cursor, "spiritualName");
          final int _cursorIndexOfMotherName = CursorUtil.getColumnIndexOrThrow(_cursor, "motherName");
          final int _cursorIndexOfFatherName = CursorUtil.getColumnIndexOrThrow(_cursor, "fatherName");
          final int _cursorIndexOfAncestry = CursorUtil.getColumnIndexOrThrow(_cursor, "ancestry");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfBloodType = CursorUtil.getColumnIndexOrThrow(_cursor, "bloodType");
          final int _cursorIndexOfDominantHand = CursorUtil.getColumnIndexOrThrow(_cursor, "dominantHand");
          final int _cursorIndexOfEyeColor = CursorUtil.getColumnIndexOrThrow(_cursor, "eyeColor");
          final int _cursorIndexOfFirstBreath = CursorUtil.getColumnIndexOrThrow(_cursor, "firstBreath");
          final int _cursorIndexOfWeatherConditions = CursorUtil.getColumnIndexOrThrow(_cursor, "weatherConditions");
          final int _cursorIndexOfMoonPhase = CursorUtil.getColumnIndexOrThrow(_cursor, "moonPhase");
          final int _cursorIndexOfHospitalName = CursorUtil.getColumnIndexOrThrow(_cursor, "hospitalName");
          final int _cursorIndexOfFirstWord = CursorUtil.getColumnIndexOrThrow(_cursor, "firstWord");
          final int _cursorIndexOfFirstSteps = CursorUtil.getColumnIndexOrThrow(_cursor, "firstSteps");
          final int _cursorIndexOfPreferences = CursorUtil.getColumnIndexOrThrow(_cursor, "preferences");
          final int _cursorIndexOfProfileCompletion = CursorUtil.getColumnIndexOrThrow(_cursor, "profileCompletion");
          final int _cursorIndexOfEnrichmentResult = CursorUtil.getColumnIndexOrThrow(_cursor, "enrichmentResult");
          final int _cursorIndexOfEnrichmentGeneratedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "enrichmentGeneratedAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfEncryptionVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "encryptionVersion");
          final List<UserProfileEntity> _result = new ArrayList<UserProfileEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final UserProfileEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpProfileName;
            _tmpProfileName = _cursor.getString(_cursorIndexOfProfileName);
            final String _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getString(_cursorIndexOfCreatedAt);
            final String _tmpLastModified;
            _tmpLastModified = _cursor.getString(_cursorIndexOfLastModified);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpDisplayName;
            if (_cursor.isNull(_cursorIndexOfDisplayName)) {
              _tmpDisplayName = null;
            } else {
              _tmpDisplayName = _cursor.getString(_cursorIndexOfDisplayName);
            }
            final String _tmpBirthDateTime;
            if (_cursor.isNull(_cursorIndexOfBirthDateTime)) {
              _tmpBirthDateTime = null;
            } else {
              _tmpBirthDateTime = _cursor.getString(_cursorIndexOfBirthDateTime);
            }
            final String _tmpBirthPlace;
            if (_cursor.isNull(_cursorIndexOfBirthPlace)) {
              _tmpBirthPlace = null;
            } else {
              _tmpBirthPlace = _cursor.getString(_cursorIndexOfBirthPlace);
            }
            final String _tmpMiddleName;
            if (_cursor.isNull(_cursorIndexOfMiddleName)) {
              _tmpMiddleName = null;
            } else {
              _tmpMiddleName = _cursor.getString(_cursorIndexOfMiddleName);
            }
            final String _tmpNickname;
            if (_cursor.isNull(_cursorIndexOfNickname)) {
              _tmpNickname = null;
            } else {
              _tmpNickname = _cursor.getString(_cursorIndexOfNickname);
            }
            final String _tmpSpiritualName;
            if (_cursor.isNull(_cursorIndexOfSpiritualName)) {
              _tmpSpiritualName = null;
            } else {
              _tmpSpiritualName = _cursor.getString(_cursorIndexOfSpiritualName);
            }
            final String _tmpMotherName;
            if (_cursor.isNull(_cursorIndexOfMotherName)) {
              _tmpMotherName = null;
            } else {
              _tmpMotherName = _cursor.getString(_cursorIndexOfMotherName);
            }
            final String _tmpFatherName;
            if (_cursor.isNull(_cursorIndexOfFatherName)) {
              _tmpFatherName = null;
            } else {
              _tmpFatherName = _cursor.getString(_cursorIndexOfFatherName);
            }
            final String _tmpAncestry;
            if (_cursor.isNull(_cursorIndexOfAncestry)) {
              _tmpAncestry = null;
            } else {
              _tmpAncestry = _cursor.getString(_cursorIndexOfAncestry);
            }
            final String _tmpGender;
            if (_cursor.isNull(_cursorIndexOfGender)) {
              _tmpGender = null;
            } else {
              _tmpGender = _cursor.getString(_cursorIndexOfGender);
            }
            final String _tmpBloodType;
            if (_cursor.isNull(_cursorIndexOfBloodType)) {
              _tmpBloodType = null;
            } else {
              _tmpBloodType = _cursor.getString(_cursorIndexOfBloodType);
            }
            final String _tmpDominantHand;
            if (_cursor.isNull(_cursorIndexOfDominantHand)) {
              _tmpDominantHand = null;
            } else {
              _tmpDominantHand = _cursor.getString(_cursorIndexOfDominantHand);
            }
            final String _tmpEyeColor;
            if (_cursor.isNull(_cursorIndexOfEyeColor)) {
              _tmpEyeColor = null;
            } else {
              _tmpEyeColor = _cursor.getString(_cursorIndexOfEyeColor);
            }
            final String _tmpFirstBreath;
            if (_cursor.isNull(_cursorIndexOfFirstBreath)) {
              _tmpFirstBreath = null;
            } else {
              _tmpFirstBreath = _cursor.getString(_cursorIndexOfFirstBreath);
            }
            final String _tmpWeatherConditions;
            if (_cursor.isNull(_cursorIndexOfWeatherConditions)) {
              _tmpWeatherConditions = null;
            } else {
              _tmpWeatherConditions = _cursor.getString(_cursorIndexOfWeatherConditions);
            }
            final String _tmpMoonPhase;
            if (_cursor.isNull(_cursorIndexOfMoonPhase)) {
              _tmpMoonPhase = null;
            } else {
              _tmpMoonPhase = _cursor.getString(_cursorIndexOfMoonPhase);
            }
            final String _tmpHospitalName;
            if (_cursor.isNull(_cursorIndexOfHospitalName)) {
              _tmpHospitalName = null;
            } else {
              _tmpHospitalName = _cursor.getString(_cursorIndexOfHospitalName);
            }
            final String _tmpFirstWord;
            if (_cursor.isNull(_cursorIndexOfFirstWord)) {
              _tmpFirstWord = null;
            } else {
              _tmpFirstWord = _cursor.getString(_cursorIndexOfFirstWord);
            }
            final String _tmpFirstSteps;
            if (_cursor.isNull(_cursorIndexOfFirstSteps)) {
              _tmpFirstSteps = null;
            } else {
              _tmpFirstSteps = _cursor.getString(_cursorIndexOfFirstSteps);
            }
            final String _tmpPreferences;
            if (_cursor.isNull(_cursorIndexOfPreferences)) {
              _tmpPreferences = null;
            } else {
              _tmpPreferences = _cursor.getString(_cursorIndexOfPreferences);
            }
            final String _tmpProfileCompletion;
            if (_cursor.isNull(_cursorIndexOfProfileCompletion)) {
              _tmpProfileCompletion = null;
            } else {
              _tmpProfileCompletion = _cursor.getString(_cursorIndexOfProfileCompletion);
            }
            final String _tmpEnrichmentResult;
            if (_cursor.isNull(_cursorIndexOfEnrichmentResult)) {
              _tmpEnrichmentResult = null;
            } else {
              _tmpEnrichmentResult = _cursor.getString(_cursorIndexOfEnrichmentResult);
            }
            final String _tmpEnrichmentGeneratedAt;
            if (_cursor.isNull(_cursorIndexOfEnrichmentGeneratedAt)) {
              _tmpEnrichmentGeneratedAt = null;
            } else {
              _tmpEnrichmentGeneratedAt = _cursor.getString(_cursorIndexOfEnrichmentGeneratedAt);
            }
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final String _tmpSyncStatus;
            _tmpSyncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
            final int _tmpEncryptionVersion;
            _tmpEncryptionVersion = _cursor.getInt(_cursorIndexOfEncryptionVersion);
            _item = new UserProfileEntity(_tmpId,_tmpProfileName,_tmpCreatedAt,_tmpLastModified,_tmpName,_tmpDisplayName,_tmpBirthDateTime,_tmpBirthPlace,_tmpMiddleName,_tmpNickname,_tmpSpiritualName,_tmpMotherName,_tmpFatherName,_tmpAncestry,_tmpGender,_tmpBloodType,_tmpDominantHand,_tmpEyeColor,_tmpFirstBreath,_tmpWeatherConditions,_tmpMoonPhase,_tmpHospitalName,_tmpFirstWord,_tmpFirstSteps,_tmpPreferences,_tmpProfileCompletion,_tmpEnrichmentResult,_tmpEnrichmentGeneratedAt,_tmpUpdatedAt,_tmpSyncStatus,_tmpEncryptionVersion);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<UserProfileEntity> getUserProfileByIdFlow(final String profileId) {
    final String _sql = "SELECT * FROM user_profiles WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, profileId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"user_profiles"}, new Callable<UserProfileEntity>() {
      @Override
      @Nullable
      public UserProfileEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProfileName = CursorUtil.getColumnIndexOrThrow(_cursor, "profileName");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDisplayName = CursorUtil.getColumnIndexOrThrow(_cursor, "displayName");
          final int _cursorIndexOfBirthDateTime = CursorUtil.getColumnIndexOrThrow(_cursor, "birthDateTime");
          final int _cursorIndexOfBirthPlace = CursorUtil.getColumnIndexOrThrow(_cursor, "birthPlace");
          final int _cursorIndexOfMiddleName = CursorUtil.getColumnIndexOrThrow(_cursor, "middleName");
          final int _cursorIndexOfNickname = CursorUtil.getColumnIndexOrThrow(_cursor, "nickname");
          final int _cursorIndexOfSpiritualName = CursorUtil.getColumnIndexOrThrow(_cursor, "spiritualName");
          final int _cursorIndexOfMotherName = CursorUtil.getColumnIndexOrThrow(_cursor, "motherName");
          final int _cursorIndexOfFatherName = CursorUtil.getColumnIndexOrThrow(_cursor, "fatherName");
          final int _cursorIndexOfAncestry = CursorUtil.getColumnIndexOrThrow(_cursor, "ancestry");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfBloodType = CursorUtil.getColumnIndexOrThrow(_cursor, "bloodType");
          final int _cursorIndexOfDominantHand = CursorUtil.getColumnIndexOrThrow(_cursor, "dominantHand");
          final int _cursorIndexOfEyeColor = CursorUtil.getColumnIndexOrThrow(_cursor, "eyeColor");
          final int _cursorIndexOfFirstBreath = CursorUtil.getColumnIndexOrThrow(_cursor, "firstBreath");
          final int _cursorIndexOfWeatherConditions = CursorUtil.getColumnIndexOrThrow(_cursor, "weatherConditions");
          final int _cursorIndexOfMoonPhase = CursorUtil.getColumnIndexOrThrow(_cursor, "moonPhase");
          final int _cursorIndexOfHospitalName = CursorUtil.getColumnIndexOrThrow(_cursor, "hospitalName");
          final int _cursorIndexOfFirstWord = CursorUtil.getColumnIndexOrThrow(_cursor, "firstWord");
          final int _cursorIndexOfFirstSteps = CursorUtil.getColumnIndexOrThrow(_cursor, "firstSteps");
          final int _cursorIndexOfPreferences = CursorUtil.getColumnIndexOrThrow(_cursor, "preferences");
          final int _cursorIndexOfProfileCompletion = CursorUtil.getColumnIndexOrThrow(_cursor, "profileCompletion");
          final int _cursorIndexOfEnrichmentResult = CursorUtil.getColumnIndexOrThrow(_cursor, "enrichmentResult");
          final int _cursorIndexOfEnrichmentGeneratedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "enrichmentGeneratedAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfEncryptionVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "encryptionVersion");
          final UserProfileEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpProfileName;
            _tmpProfileName = _cursor.getString(_cursorIndexOfProfileName);
            final String _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getString(_cursorIndexOfCreatedAt);
            final String _tmpLastModified;
            _tmpLastModified = _cursor.getString(_cursorIndexOfLastModified);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpDisplayName;
            if (_cursor.isNull(_cursorIndexOfDisplayName)) {
              _tmpDisplayName = null;
            } else {
              _tmpDisplayName = _cursor.getString(_cursorIndexOfDisplayName);
            }
            final String _tmpBirthDateTime;
            if (_cursor.isNull(_cursorIndexOfBirthDateTime)) {
              _tmpBirthDateTime = null;
            } else {
              _tmpBirthDateTime = _cursor.getString(_cursorIndexOfBirthDateTime);
            }
            final String _tmpBirthPlace;
            if (_cursor.isNull(_cursorIndexOfBirthPlace)) {
              _tmpBirthPlace = null;
            } else {
              _tmpBirthPlace = _cursor.getString(_cursorIndexOfBirthPlace);
            }
            final String _tmpMiddleName;
            if (_cursor.isNull(_cursorIndexOfMiddleName)) {
              _tmpMiddleName = null;
            } else {
              _tmpMiddleName = _cursor.getString(_cursorIndexOfMiddleName);
            }
            final String _tmpNickname;
            if (_cursor.isNull(_cursorIndexOfNickname)) {
              _tmpNickname = null;
            } else {
              _tmpNickname = _cursor.getString(_cursorIndexOfNickname);
            }
            final String _tmpSpiritualName;
            if (_cursor.isNull(_cursorIndexOfSpiritualName)) {
              _tmpSpiritualName = null;
            } else {
              _tmpSpiritualName = _cursor.getString(_cursorIndexOfSpiritualName);
            }
            final String _tmpMotherName;
            if (_cursor.isNull(_cursorIndexOfMotherName)) {
              _tmpMotherName = null;
            } else {
              _tmpMotherName = _cursor.getString(_cursorIndexOfMotherName);
            }
            final String _tmpFatherName;
            if (_cursor.isNull(_cursorIndexOfFatherName)) {
              _tmpFatherName = null;
            } else {
              _tmpFatherName = _cursor.getString(_cursorIndexOfFatherName);
            }
            final String _tmpAncestry;
            if (_cursor.isNull(_cursorIndexOfAncestry)) {
              _tmpAncestry = null;
            } else {
              _tmpAncestry = _cursor.getString(_cursorIndexOfAncestry);
            }
            final String _tmpGender;
            if (_cursor.isNull(_cursorIndexOfGender)) {
              _tmpGender = null;
            } else {
              _tmpGender = _cursor.getString(_cursorIndexOfGender);
            }
            final String _tmpBloodType;
            if (_cursor.isNull(_cursorIndexOfBloodType)) {
              _tmpBloodType = null;
            } else {
              _tmpBloodType = _cursor.getString(_cursorIndexOfBloodType);
            }
            final String _tmpDominantHand;
            if (_cursor.isNull(_cursorIndexOfDominantHand)) {
              _tmpDominantHand = null;
            } else {
              _tmpDominantHand = _cursor.getString(_cursorIndexOfDominantHand);
            }
            final String _tmpEyeColor;
            if (_cursor.isNull(_cursorIndexOfEyeColor)) {
              _tmpEyeColor = null;
            } else {
              _tmpEyeColor = _cursor.getString(_cursorIndexOfEyeColor);
            }
            final String _tmpFirstBreath;
            if (_cursor.isNull(_cursorIndexOfFirstBreath)) {
              _tmpFirstBreath = null;
            } else {
              _tmpFirstBreath = _cursor.getString(_cursorIndexOfFirstBreath);
            }
            final String _tmpWeatherConditions;
            if (_cursor.isNull(_cursorIndexOfWeatherConditions)) {
              _tmpWeatherConditions = null;
            } else {
              _tmpWeatherConditions = _cursor.getString(_cursorIndexOfWeatherConditions);
            }
            final String _tmpMoonPhase;
            if (_cursor.isNull(_cursorIndexOfMoonPhase)) {
              _tmpMoonPhase = null;
            } else {
              _tmpMoonPhase = _cursor.getString(_cursorIndexOfMoonPhase);
            }
            final String _tmpHospitalName;
            if (_cursor.isNull(_cursorIndexOfHospitalName)) {
              _tmpHospitalName = null;
            } else {
              _tmpHospitalName = _cursor.getString(_cursorIndexOfHospitalName);
            }
            final String _tmpFirstWord;
            if (_cursor.isNull(_cursorIndexOfFirstWord)) {
              _tmpFirstWord = null;
            } else {
              _tmpFirstWord = _cursor.getString(_cursorIndexOfFirstWord);
            }
            final String _tmpFirstSteps;
            if (_cursor.isNull(_cursorIndexOfFirstSteps)) {
              _tmpFirstSteps = null;
            } else {
              _tmpFirstSteps = _cursor.getString(_cursorIndexOfFirstSteps);
            }
            final String _tmpPreferences;
            if (_cursor.isNull(_cursorIndexOfPreferences)) {
              _tmpPreferences = null;
            } else {
              _tmpPreferences = _cursor.getString(_cursorIndexOfPreferences);
            }
            final String _tmpProfileCompletion;
            if (_cursor.isNull(_cursorIndexOfProfileCompletion)) {
              _tmpProfileCompletion = null;
            } else {
              _tmpProfileCompletion = _cursor.getString(_cursorIndexOfProfileCompletion);
            }
            final String _tmpEnrichmentResult;
            if (_cursor.isNull(_cursorIndexOfEnrichmentResult)) {
              _tmpEnrichmentResult = null;
            } else {
              _tmpEnrichmentResult = _cursor.getString(_cursorIndexOfEnrichmentResult);
            }
            final String _tmpEnrichmentGeneratedAt;
            if (_cursor.isNull(_cursorIndexOfEnrichmentGeneratedAt)) {
              _tmpEnrichmentGeneratedAt = null;
            } else {
              _tmpEnrichmentGeneratedAt = _cursor.getString(_cursorIndexOfEnrichmentGeneratedAt);
            }
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final String _tmpSyncStatus;
            _tmpSyncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
            final int _tmpEncryptionVersion;
            _tmpEncryptionVersion = _cursor.getInt(_cursorIndexOfEncryptionVersion);
            _result = new UserProfileEntity(_tmpId,_tmpProfileName,_tmpCreatedAt,_tmpLastModified,_tmpName,_tmpDisplayName,_tmpBirthDateTime,_tmpBirthPlace,_tmpMiddleName,_tmpNickname,_tmpSpiritualName,_tmpMotherName,_tmpFatherName,_tmpAncestry,_tmpGender,_tmpBloodType,_tmpDominantHand,_tmpEyeColor,_tmpFirstBreath,_tmpWeatherConditions,_tmpMoonPhase,_tmpHospitalName,_tmpFirstWord,_tmpFirstSteps,_tmpPreferences,_tmpProfileCompletion,_tmpEnrichmentResult,_tmpEnrichmentGeneratedAt,_tmpUpdatedAt,_tmpSyncStatus,_tmpEncryptionVersion);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object searchProfiles(final String query,
      final Continuation<? super List<UserProfileEntity>> $completion) {
    final String _sql = "SELECT * FROM user_profiles WHERE profileName LIKE '%' || ? || '%' OR name LIKE '%' || ? || '%' ORDER BY updatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    _argIndex = 2;
    _statement.bindString(_argIndex, query);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<UserProfileEntity>>() {
      @Override
      @NonNull
      public List<UserProfileEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProfileName = CursorUtil.getColumnIndexOrThrow(_cursor, "profileName");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDisplayName = CursorUtil.getColumnIndexOrThrow(_cursor, "displayName");
          final int _cursorIndexOfBirthDateTime = CursorUtil.getColumnIndexOrThrow(_cursor, "birthDateTime");
          final int _cursorIndexOfBirthPlace = CursorUtil.getColumnIndexOrThrow(_cursor, "birthPlace");
          final int _cursorIndexOfMiddleName = CursorUtil.getColumnIndexOrThrow(_cursor, "middleName");
          final int _cursorIndexOfNickname = CursorUtil.getColumnIndexOrThrow(_cursor, "nickname");
          final int _cursorIndexOfSpiritualName = CursorUtil.getColumnIndexOrThrow(_cursor, "spiritualName");
          final int _cursorIndexOfMotherName = CursorUtil.getColumnIndexOrThrow(_cursor, "motherName");
          final int _cursorIndexOfFatherName = CursorUtil.getColumnIndexOrThrow(_cursor, "fatherName");
          final int _cursorIndexOfAncestry = CursorUtil.getColumnIndexOrThrow(_cursor, "ancestry");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfBloodType = CursorUtil.getColumnIndexOrThrow(_cursor, "bloodType");
          final int _cursorIndexOfDominantHand = CursorUtil.getColumnIndexOrThrow(_cursor, "dominantHand");
          final int _cursorIndexOfEyeColor = CursorUtil.getColumnIndexOrThrow(_cursor, "eyeColor");
          final int _cursorIndexOfFirstBreath = CursorUtil.getColumnIndexOrThrow(_cursor, "firstBreath");
          final int _cursorIndexOfWeatherConditions = CursorUtil.getColumnIndexOrThrow(_cursor, "weatherConditions");
          final int _cursorIndexOfMoonPhase = CursorUtil.getColumnIndexOrThrow(_cursor, "moonPhase");
          final int _cursorIndexOfHospitalName = CursorUtil.getColumnIndexOrThrow(_cursor, "hospitalName");
          final int _cursorIndexOfFirstWord = CursorUtil.getColumnIndexOrThrow(_cursor, "firstWord");
          final int _cursorIndexOfFirstSteps = CursorUtil.getColumnIndexOrThrow(_cursor, "firstSteps");
          final int _cursorIndexOfPreferences = CursorUtil.getColumnIndexOrThrow(_cursor, "preferences");
          final int _cursorIndexOfProfileCompletion = CursorUtil.getColumnIndexOrThrow(_cursor, "profileCompletion");
          final int _cursorIndexOfEnrichmentResult = CursorUtil.getColumnIndexOrThrow(_cursor, "enrichmentResult");
          final int _cursorIndexOfEnrichmentGeneratedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "enrichmentGeneratedAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfEncryptionVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "encryptionVersion");
          final List<UserProfileEntity> _result = new ArrayList<UserProfileEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final UserProfileEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpProfileName;
            _tmpProfileName = _cursor.getString(_cursorIndexOfProfileName);
            final String _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getString(_cursorIndexOfCreatedAt);
            final String _tmpLastModified;
            _tmpLastModified = _cursor.getString(_cursorIndexOfLastModified);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpDisplayName;
            if (_cursor.isNull(_cursorIndexOfDisplayName)) {
              _tmpDisplayName = null;
            } else {
              _tmpDisplayName = _cursor.getString(_cursorIndexOfDisplayName);
            }
            final String _tmpBirthDateTime;
            if (_cursor.isNull(_cursorIndexOfBirthDateTime)) {
              _tmpBirthDateTime = null;
            } else {
              _tmpBirthDateTime = _cursor.getString(_cursorIndexOfBirthDateTime);
            }
            final String _tmpBirthPlace;
            if (_cursor.isNull(_cursorIndexOfBirthPlace)) {
              _tmpBirthPlace = null;
            } else {
              _tmpBirthPlace = _cursor.getString(_cursorIndexOfBirthPlace);
            }
            final String _tmpMiddleName;
            if (_cursor.isNull(_cursorIndexOfMiddleName)) {
              _tmpMiddleName = null;
            } else {
              _tmpMiddleName = _cursor.getString(_cursorIndexOfMiddleName);
            }
            final String _tmpNickname;
            if (_cursor.isNull(_cursorIndexOfNickname)) {
              _tmpNickname = null;
            } else {
              _tmpNickname = _cursor.getString(_cursorIndexOfNickname);
            }
            final String _tmpSpiritualName;
            if (_cursor.isNull(_cursorIndexOfSpiritualName)) {
              _tmpSpiritualName = null;
            } else {
              _tmpSpiritualName = _cursor.getString(_cursorIndexOfSpiritualName);
            }
            final String _tmpMotherName;
            if (_cursor.isNull(_cursorIndexOfMotherName)) {
              _tmpMotherName = null;
            } else {
              _tmpMotherName = _cursor.getString(_cursorIndexOfMotherName);
            }
            final String _tmpFatherName;
            if (_cursor.isNull(_cursorIndexOfFatherName)) {
              _tmpFatherName = null;
            } else {
              _tmpFatherName = _cursor.getString(_cursorIndexOfFatherName);
            }
            final String _tmpAncestry;
            if (_cursor.isNull(_cursorIndexOfAncestry)) {
              _tmpAncestry = null;
            } else {
              _tmpAncestry = _cursor.getString(_cursorIndexOfAncestry);
            }
            final String _tmpGender;
            if (_cursor.isNull(_cursorIndexOfGender)) {
              _tmpGender = null;
            } else {
              _tmpGender = _cursor.getString(_cursorIndexOfGender);
            }
            final String _tmpBloodType;
            if (_cursor.isNull(_cursorIndexOfBloodType)) {
              _tmpBloodType = null;
            } else {
              _tmpBloodType = _cursor.getString(_cursorIndexOfBloodType);
            }
            final String _tmpDominantHand;
            if (_cursor.isNull(_cursorIndexOfDominantHand)) {
              _tmpDominantHand = null;
            } else {
              _tmpDominantHand = _cursor.getString(_cursorIndexOfDominantHand);
            }
            final String _tmpEyeColor;
            if (_cursor.isNull(_cursorIndexOfEyeColor)) {
              _tmpEyeColor = null;
            } else {
              _tmpEyeColor = _cursor.getString(_cursorIndexOfEyeColor);
            }
            final String _tmpFirstBreath;
            if (_cursor.isNull(_cursorIndexOfFirstBreath)) {
              _tmpFirstBreath = null;
            } else {
              _tmpFirstBreath = _cursor.getString(_cursorIndexOfFirstBreath);
            }
            final String _tmpWeatherConditions;
            if (_cursor.isNull(_cursorIndexOfWeatherConditions)) {
              _tmpWeatherConditions = null;
            } else {
              _tmpWeatherConditions = _cursor.getString(_cursorIndexOfWeatherConditions);
            }
            final String _tmpMoonPhase;
            if (_cursor.isNull(_cursorIndexOfMoonPhase)) {
              _tmpMoonPhase = null;
            } else {
              _tmpMoonPhase = _cursor.getString(_cursorIndexOfMoonPhase);
            }
            final String _tmpHospitalName;
            if (_cursor.isNull(_cursorIndexOfHospitalName)) {
              _tmpHospitalName = null;
            } else {
              _tmpHospitalName = _cursor.getString(_cursorIndexOfHospitalName);
            }
            final String _tmpFirstWord;
            if (_cursor.isNull(_cursorIndexOfFirstWord)) {
              _tmpFirstWord = null;
            } else {
              _tmpFirstWord = _cursor.getString(_cursorIndexOfFirstWord);
            }
            final String _tmpFirstSteps;
            if (_cursor.isNull(_cursorIndexOfFirstSteps)) {
              _tmpFirstSteps = null;
            } else {
              _tmpFirstSteps = _cursor.getString(_cursorIndexOfFirstSteps);
            }
            final String _tmpPreferences;
            if (_cursor.isNull(_cursorIndexOfPreferences)) {
              _tmpPreferences = null;
            } else {
              _tmpPreferences = _cursor.getString(_cursorIndexOfPreferences);
            }
            final String _tmpProfileCompletion;
            if (_cursor.isNull(_cursorIndexOfProfileCompletion)) {
              _tmpProfileCompletion = null;
            } else {
              _tmpProfileCompletion = _cursor.getString(_cursorIndexOfProfileCompletion);
            }
            final String _tmpEnrichmentResult;
            if (_cursor.isNull(_cursorIndexOfEnrichmentResult)) {
              _tmpEnrichmentResult = null;
            } else {
              _tmpEnrichmentResult = _cursor.getString(_cursorIndexOfEnrichmentResult);
            }
            final String _tmpEnrichmentGeneratedAt;
            if (_cursor.isNull(_cursorIndexOfEnrichmentGeneratedAt)) {
              _tmpEnrichmentGeneratedAt = null;
            } else {
              _tmpEnrichmentGeneratedAt = _cursor.getString(_cursorIndexOfEnrichmentGeneratedAt);
            }
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final String _tmpSyncStatus;
            _tmpSyncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
            final int _tmpEncryptionVersion;
            _tmpEncryptionVersion = _cursor.getInt(_cursorIndexOfEncryptionVersion);
            _item = new UserProfileEntity(_tmpId,_tmpProfileName,_tmpCreatedAt,_tmpLastModified,_tmpName,_tmpDisplayName,_tmpBirthDateTime,_tmpBirthPlace,_tmpMiddleName,_tmpNickname,_tmpSpiritualName,_tmpMotherName,_tmpFatherName,_tmpAncestry,_tmpGender,_tmpBloodType,_tmpDominantHand,_tmpEyeColor,_tmpFirstBreath,_tmpWeatherConditions,_tmpMoonPhase,_tmpHospitalName,_tmpFirstWord,_tmpFirstSteps,_tmpPreferences,_tmpProfileCompletion,_tmpEnrichmentResult,_tmpEnrichmentGeneratedAt,_tmpUpdatedAt,_tmpSyncStatus,_tmpEncryptionVersion);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getProfilesNeedingSync(
      final Continuation<? super List<UserProfileEntity>> $completion) {
    final String _sql = "SELECT * FROM user_profiles WHERE syncStatus = 'LOCAL' OR syncStatus = 'SYNCING'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<UserProfileEntity>>() {
      @Override
      @NonNull
      public List<UserProfileEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProfileName = CursorUtil.getColumnIndexOrThrow(_cursor, "profileName");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDisplayName = CursorUtil.getColumnIndexOrThrow(_cursor, "displayName");
          final int _cursorIndexOfBirthDateTime = CursorUtil.getColumnIndexOrThrow(_cursor, "birthDateTime");
          final int _cursorIndexOfBirthPlace = CursorUtil.getColumnIndexOrThrow(_cursor, "birthPlace");
          final int _cursorIndexOfMiddleName = CursorUtil.getColumnIndexOrThrow(_cursor, "middleName");
          final int _cursorIndexOfNickname = CursorUtil.getColumnIndexOrThrow(_cursor, "nickname");
          final int _cursorIndexOfSpiritualName = CursorUtil.getColumnIndexOrThrow(_cursor, "spiritualName");
          final int _cursorIndexOfMotherName = CursorUtil.getColumnIndexOrThrow(_cursor, "motherName");
          final int _cursorIndexOfFatherName = CursorUtil.getColumnIndexOrThrow(_cursor, "fatherName");
          final int _cursorIndexOfAncestry = CursorUtil.getColumnIndexOrThrow(_cursor, "ancestry");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfBloodType = CursorUtil.getColumnIndexOrThrow(_cursor, "bloodType");
          final int _cursorIndexOfDominantHand = CursorUtil.getColumnIndexOrThrow(_cursor, "dominantHand");
          final int _cursorIndexOfEyeColor = CursorUtil.getColumnIndexOrThrow(_cursor, "eyeColor");
          final int _cursorIndexOfFirstBreath = CursorUtil.getColumnIndexOrThrow(_cursor, "firstBreath");
          final int _cursorIndexOfWeatherConditions = CursorUtil.getColumnIndexOrThrow(_cursor, "weatherConditions");
          final int _cursorIndexOfMoonPhase = CursorUtil.getColumnIndexOrThrow(_cursor, "moonPhase");
          final int _cursorIndexOfHospitalName = CursorUtil.getColumnIndexOrThrow(_cursor, "hospitalName");
          final int _cursorIndexOfFirstWord = CursorUtil.getColumnIndexOrThrow(_cursor, "firstWord");
          final int _cursorIndexOfFirstSteps = CursorUtil.getColumnIndexOrThrow(_cursor, "firstSteps");
          final int _cursorIndexOfPreferences = CursorUtil.getColumnIndexOrThrow(_cursor, "preferences");
          final int _cursorIndexOfProfileCompletion = CursorUtil.getColumnIndexOrThrow(_cursor, "profileCompletion");
          final int _cursorIndexOfEnrichmentResult = CursorUtil.getColumnIndexOrThrow(_cursor, "enrichmentResult");
          final int _cursorIndexOfEnrichmentGeneratedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "enrichmentGeneratedAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfEncryptionVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "encryptionVersion");
          final List<UserProfileEntity> _result = new ArrayList<UserProfileEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final UserProfileEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpProfileName;
            _tmpProfileName = _cursor.getString(_cursorIndexOfProfileName);
            final String _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getString(_cursorIndexOfCreatedAt);
            final String _tmpLastModified;
            _tmpLastModified = _cursor.getString(_cursorIndexOfLastModified);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpDisplayName;
            if (_cursor.isNull(_cursorIndexOfDisplayName)) {
              _tmpDisplayName = null;
            } else {
              _tmpDisplayName = _cursor.getString(_cursorIndexOfDisplayName);
            }
            final String _tmpBirthDateTime;
            if (_cursor.isNull(_cursorIndexOfBirthDateTime)) {
              _tmpBirthDateTime = null;
            } else {
              _tmpBirthDateTime = _cursor.getString(_cursorIndexOfBirthDateTime);
            }
            final String _tmpBirthPlace;
            if (_cursor.isNull(_cursorIndexOfBirthPlace)) {
              _tmpBirthPlace = null;
            } else {
              _tmpBirthPlace = _cursor.getString(_cursorIndexOfBirthPlace);
            }
            final String _tmpMiddleName;
            if (_cursor.isNull(_cursorIndexOfMiddleName)) {
              _tmpMiddleName = null;
            } else {
              _tmpMiddleName = _cursor.getString(_cursorIndexOfMiddleName);
            }
            final String _tmpNickname;
            if (_cursor.isNull(_cursorIndexOfNickname)) {
              _tmpNickname = null;
            } else {
              _tmpNickname = _cursor.getString(_cursorIndexOfNickname);
            }
            final String _tmpSpiritualName;
            if (_cursor.isNull(_cursorIndexOfSpiritualName)) {
              _tmpSpiritualName = null;
            } else {
              _tmpSpiritualName = _cursor.getString(_cursorIndexOfSpiritualName);
            }
            final String _tmpMotherName;
            if (_cursor.isNull(_cursorIndexOfMotherName)) {
              _tmpMotherName = null;
            } else {
              _tmpMotherName = _cursor.getString(_cursorIndexOfMotherName);
            }
            final String _tmpFatherName;
            if (_cursor.isNull(_cursorIndexOfFatherName)) {
              _tmpFatherName = null;
            } else {
              _tmpFatherName = _cursor.getString(_cursorIndexOfFatherName);
            }
            final String _tmpAncestry;
            if (_cursor.isNull(_cursorIndexOfAncestry)) {
              _tmpAncestry = null;
            } else {
              _tmpAncestry = _cursor.getString(_cursorIndexOfAncestry);
            }
            final String _tmpGender;
            if (_cursor.isNull(_cursorIndexOfGender)) {
              _tmpGender = null;
            } else {
              _tmpGender = _cursor.getString(_cursorIndexOfGender);
            }
            final String _tmpBloodType;
            if (_cursor.isNull(_cursorIndexOfBloodType)) {
              _tmpBloodType = null;
            } else {
              _tmpBloodType = _cursor.getString(_cursorIndexOfBloodType);
            }
            final String _tmpDominantHand;
            if (_cursor.isNull(_cursorIndexOfDominantHand)) {
              _tmpDominantHand = null;
            } else {
              _tmpDominantHand = _cursor.getString(_cursorIndexOfDominantHand);
            }
            final String _tmpEyeColor;
            if (_cursor.isNull(_cursorIndexOfEyeColor)) {
              _tmpEyeColor = null;
            } else {
              _tmpEyeColor = _cursor.getString(_cursorIndexOfEyeColor);
            }
            final String _tmpFirstBreath;
            if (_cursor.isNull(_cursorIndexOfFirstBreath)) {
              _tmpFirstBreath = null;
            } else {
              _tmpFirstBreath = _cursor.getString(_cursorIndexOfFirstBreath);
            }
            final String _tmpWeatherConditions;
            if (_cursor.isNull(_cursorIndexOfWeatherConditions)) {
              _tmpWeatherConditions = null;
            } else {
              _tmpWeatherConditions = _cursor.getString(_cursorIndexOfWeatherConditions);
            }
            final String _tmpMoonPhase;
            if (_cursor.isNull(_cursorIndexOfMoonPhase)) {
              _tmpMoonPhase = null;
            } else {
              _tmpMoonPhase = _cursor.getString(_cursorIndexOfMoonPhase);
            }
            final String _tmpHospitalName;
            if (_cursor.isNull(_cursorIndexOfHospitalName)) {
              _tmpHospitalName = null;
            } else {
              _tmpHospitalName = _cursor.getString(_cursorIndexOfHospitalName);
            }
            final String _tmpFirstWord;
            if (_cursor.isNull(_cursorIndexOfFirstWord)) {
              _tmpFirstWord = null;
            } else {
              _tmpFirstWord = _cursor.getString(_cursorIndexOfFirstWord);
            }
            final String _tmpFirstSteps;
            if (_cursor.isNull(_cursorIndexOfFirstSteps)) {
              _tmpFirstSteps = null;
            } else {
              _tmpFirstSteps = _cursor.getString(_cursorIndexOfFirstSteps);
            }
            final String _tmpPreferences;
            if (_cursor.isNull(_cursorIndexOfPreferences)) {
              _tmpPreferences = null;
            } else {
              _tmpPreferences = _cursor.getString(_cursorIndexOfPreferences);
            }
            final String _tmpProfileCompletion;
            if (_cursor.isNull(_cursorIndexOfProfileCompletion)) {
              _tmpProfileCompletion = null;
            } else {
              _tmpProfileCompletion = _cursor.getString(_cursorIndexOfProfileCompletion);
            }
            final String _tmpEnrichmentResult;
            if (_cursor.isNull(_cursorIndexOfEnrichmentResult)) {
              _tmpEnrichmentResult = null;
            } else {
              _tmpEnrichmentResult = _cursor.getString(_cursorIndexOfEnrichmentResult);
            }
            final String _tmpEnrichmentGeneratedAt;
            if (_cursor.isNull(_cursorIndexOfEnrichmentGeneratedAt)) {
              _tmpEnrichmentGeneratedAt = null;
            } else {
              _tmpEnrichmentGeneratedAt = _cursor.getString(_cursorIndexOfEnrichmentGeneratedAt);
            }
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final String _tmpSyncStatus;
            _tmpSyncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
            final int _tmpEncryptionVersion;
            _tmpEncryptionVersion = _cursor.getInt(_cursorIndexOfEncryptionVersion);
            _item = new UserProfileEntity(_tmpId,_tmpProfileName,_tmpCreatedAt,_tmpLastModified,_tmpName,_tmpDisplayName,_tmpBirthDateTime,_tmpBirthPlace,_tmpMiddleName,_tmpNickname,_tmpSpiritualName,_tmpMotherName,_tmpFatherName,_tmpAncestry,_tmpGender,_tmpBloodType,_tmpDominantHand,_tmpEyeColor,_tmpFirstBreath,_tmpWeatherConditions,_tmpMoonPhase,_tmpHospitalName,_tmpFirstWord,_tmpFirstSteps,_tmpPreferences,_tmpProfileCompletion,_tmpEnrichmentResult,_tmpEnrichmentGeneratedAt,_tmpUpdatedAt,_tmpSyncStatus,_tmpEncryptionVersion);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getProfileCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM user_profiles";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
