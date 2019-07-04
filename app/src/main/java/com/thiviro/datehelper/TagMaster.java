package com.thiviro.datehelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * TAG MASTER
 *
 * This class gathers all the Tags, to make sure that there is only one instance of each Tag and its votes
 *
 * @author Thiago
 * @version 1
 * @since 1
 */
public class TagMaster {
  private Map<String, Tag> allTags;

  public TagMaster(){
    allTags = new HashMap<>();
  }

  /**
   * Add a new Tag to the base.
   *
   * If the tag already exists, it will return the key to find it
   * If it does not exist, add it and then return the key.
   *
   * @param tag The tag to be added to Tag Master
   * @return key A key to retrieve this tag latter
   */
  public String addTag (Tag tag) {
    if(allTags.containsValue(tag)){
      return allTags.get(tag.getTagName()).getTagName();
    }else {
      allTags.put(tag.getTagName(),tag);
      return allTags.get(tag.getTagName()).getTagName();
    }
  }

  /**
   * Get a List of all the Tags currently in the Tag master
   * @return tagsList List of all Tags
   */
  public List<Tag> getAllTags() {
    ArrayList<Tag> tagsList = new ArrayList<>(allTags.values());
    return tagsList;
  }

  /**
   * Get the tags using the keys
   *
   * If the list of keys is empty it will return null
   * otherwise it will create a list and feed it with the tags matching the keys
   *
   * @param keys list of keys to find tags
   * @return tagsRetrieved the tags correspondent to the keys
   */
  List<Tag> getTagsPack (List<String> keys) {
    if(keys.size() == 0){
      return null;
    }
    List<Tag> tagsRetrieved = new ArrayList<>();
    for(String key : keys){
      if(allTags.containsKey(key)){
        tagsRetrieved.add(allTags.get(key));
      }
    }
    return tagsRetrieved;
  }
}
