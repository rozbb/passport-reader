/*
 * JMRTD - A Java API for accessing machine readable travel documents.
 *
 * Copyright (C) 2006 - 2021  The JMRTD team
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 *
 * $Id: $
 */

package org.jmrtd;

import net.sf.scuba.smartcards.CardServiceException;

/**
 * An exception to signal errors during execution of a protocol.
 *
 * @author The JMRTD team (info@jmrtd.org)
 *
 * @version $Revision: $
 *
 * @since 0.7.27
 */
public class CardServiceProtocolException extends CardServiceException {

  private static final long serialVersionUID = 8527846223511524125L;

  /** Identifies the protocol step that failed. */
  private int step;

  /**
   * Creates a {@code CardServiceProtocolException}.
   *
   * @param msg a message
   * @param step the protocol step that failed
   */
  public CardServiceProtocolException(String msg, int step) {
    super(msg);
    this.step = step;
  }

  /**
   * Creates a {@code CardServiceProtocolException}.
   *
   * @param msg a message
   * @param step the protocol step that failed
   * @param cause the exception causing this exception
   */
  public CardServiceProtocolException(String msg, int step, Throwable cause) {
    super(msg, cause);
    this.step = step;
  }

  /**
   * Creates a {@code CardServiceProtocolException} with a specific status word.
   *
   * @param msg a message
   * @param step the protocol step that failed
   * @param sw the status word that caused this CardServiceException
   */
  public CardServiceProtocolException(String msg, int step, int sw) {
    super(msg, sw);
    this.step = step;
  }

  /**
   * Creates a {@code CardServiceProtocolException} with a specific status word.
   *
   * @param msg a message
   * @param step the protocol step that failed
   * @param cause the exception causing this exception
   * @param sw the status word that caused this CardServiceException
   */
  public CardServiceProtocolException(String msg, int step, Throwable cause, int sw) {
    super(msg, cause, sw);
    this.step = step;
  }

  /**
   * Identifies the protocol step that failed.
   *
   * @return the protocol step that failed
   */
  public int getStep() {
    return step;
  }

  @Override
  public String getMessage() {
    return new StringBuilder()
        .append(super.getMessage())
        .append(" (").append("step: ").append(step).append(")")
        .toString();
  }
}
