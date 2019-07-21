package com.thiviro.datehelper;

import android.app.Activity;

import java.util.ArrayList;

public class GetAPIWorker extends APIWorker {

  private PreferenceHandler prefHandler;


  public GetAPIWorker(Activity activity, String endpoint, String method){
    super(activity, endpoint, method);
  }

  @Override
  public void onPostExecute(String response) {
    if (!response.equals("")){
      Gender gender = prefHandler.getGender();
      String photoURL = prefHandler.getPhotoURL();
      String id = prefHandler.getId();
      Person mPerson =
          new Person(prefHandler.getFirstName(), prefHandler.getLastName(),
              gender, new ArrayList<Tag>(), new TagMaster());
      Account mAccount = new Account(mPerson, id, photoURL);
      mAccount.setStudyArea(prefHandler.getStudyArea());

      UserPutAPIWorker userPutAPIWorker =
          new UserPutAPIWorker(activity.get(), APIWorker.ENDPOINT_USERS + "/" + id,
              APIWorker.PUT, mAccount);
      userPutAPIWorker.execute();
    }


  }
}

