/*
 * JMRTD - A Java API for accessing machine readable travel documents.
 *
 * Copyright (C) 2006 - 2018  The JMRTD team
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
 * $Id: PACEException.java 1851 2021-05-27 20:56:53Z martijno $
 */

package org.jmrtd;

/**
 * An exception to signal errors during execution of the PACE protocol.
 *
 * @author The JMRTD team (info@jmrtd.org)
 *
 * @version $Revision: 1851 $
 *
 * @deprecated Use {@link CardServiceProtocolException} instead.
 */
@Deprecated
public class PACEException extends CardServiceProtocolException {

  private static final long serialVersionUID = 8383980807753919040L;

  /**
   * Creates a {@code PACEException}.
   *
   * @param msg a message
   * @param step the protocol step that failed
   */
  public PACEException(String msg, int step) {
    super(msg, step);
  }

  /**
   * Creates a {@code PACEException}.
   *
   * @param msg a message
   * @param step the protocol step that failed
   * @param cause the exception causing this exception
   */
  public PACEException(String msg, int step, Throwable cause) {
    super(msg, step, cause);
  }

  /**
   * Creates a PACEException with a specific status word.
   *
   * @param msg a message
   * @param step the protocol step that failed
   * @param sw the status word that caused this CardServiceException
   */
  public PACEException(String msg, int step, int sw) {
    super(msg, step, sw);
  }

  /**
   * Creates a PACEException with a specific status word.
   *
   * @param msg a message
   * @param step the protocol step that failed
   * @param cause the exception causing this exception
   * @param sw the status word that caused this CardServiceException
   */
  public PACEException(String msg, int step, Throwable cause, int sw) {
    super(msg, step, cause, sw);
  }
}
