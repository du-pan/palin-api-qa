package com.palin.api.qa.util;

import static com.palin.api.qa.config.BaseTest.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import org.apache.commons.lang3.tuple.Pair;

public class ObservableValue<T> {
  private T value;
  private final List<BiConsumer<T, T>> listeners = new ArrayList<>();

  public void set(T newValue) {
    T oldValue = this.value;
    this.value = newValue;
    if (oldValue != newValue) {
      listeners.forEach(listener -> listener.accept(oldValue, newValue));
    }
  }

  public T get() {
    return value;
  }

  public void remove() {
    this.value = null;
  }

  /**
   * Listener will track change value for variable declared as <ObservableValue> type. When variable
   * value is changed, listener method call will store it values in the proper set of data.
   *
   * @param listener for changed value
   * @author Dušan Panić
   */
  public void addChangeListener(BiConsumer<T, T> listener) {
    listeners.add(listener);
  }

  /**
   * Observer and listener activator for the User related variables.
   *
   * <p>{@code accessToken} -> of logged-in user with action permission(s)
   *
   * <p>{@code createUserId} -> of user which has been created and have to be deleted when test ends
   *
   * @author Dušan Panić
   */
  public static void setUserApiValuesObserver() {
    // Only add once
    accessToken.addChangeListener(
        (oldVal, newVal) -> {
          // No action here – we track pair when accessToken is changed
        });

    createdUserId.addChangeListener(
        (oldVal, newVal) -> {
          if (accessToken.get() != null && newVal != null) {
            userCleanUpList.add(Pair.of(accessToken.get(), newVal));
          }
        });

    createdProductId.addChangeListener(
        (oldVal, newVal) -> {
          if (accessToken.get() != null && newVal != null) {
            productCleanUpList.add(Pair.of(accessToken.get(), newVal));
          }
        });
  }
}
