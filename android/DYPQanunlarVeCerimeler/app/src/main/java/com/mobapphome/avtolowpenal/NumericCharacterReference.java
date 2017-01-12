package com.mobapphome.avtolowpenal;

/* ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is part of dcm4che, an implementation of DICOM(TM) in
 * Java(TM), available at http://sourceforge.net/projects/dcm4che.
 *
 * The Initial Developer of the Original Code is
 * TIANI Medgraph AG.
 * Portions created by the Initial Developer are Copyright (C) 2003-2005
 * the Initial Developer. All Rights Reserved.
 *
 * Contributor(s):
 * Gunter Zeilinger <gunter.zeilinger@tiani.com>
 * Franz Willer <franz.willer@gwi-ag.com>
 *
 * Alternatively, the contents of this file may be used under the terms of
 * either the GNU General Public License Version 2 or later (the "GPL"), or
 * the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the MPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the MPL, the GPL or the LGPL.
 *
 * ***** END LICENSE BLOCK ***** */



/**
 *
 * Helper class to encode/decode Strings with Numeric Character References as defined in SGML.
 * 
 * @author franz.willer
 *
 * @version $Revision: 2101 $
 * @since 25.11.2005
 */
public class NumericCharacterReference {

  /**
   * Decodes a String with Numeric Character References.
   * <p>
   * 
   * @param str A NCR encoded String
   * @param unknownCh, A character that is used if nnnn of &#nnnn; is not a int.
   * 
   * @return The decoded String.
   */
   public static String decode(String str, char unknownCh) {
          StringBuffer sb = new StringBuffer();
          int i1=0;
          int i2=0;

          while(i2<str.length()) {
             i1 = str.indexOf("&#",i2);
             if (i1 == -1 ) {
                  sb.append(str.substring(i2));
                  break ;
             }
             sb.append(str.substring(i2, i1));
             i2 = str.indexOf(";", i1);
             if (i2 == -1 ) {
                  sb.append(str.substring(i1));
                  break ;
             }

             String tok = str.substring(i1+2, i2);
              try {
                   int radix = 10 ;
                   if (tok.charAt(0) == 'x' || tok.charAt(0) == 'X') {
                      radix = 16 ;
                      tok = tok.substring(1);
                   }
                   sb.append((char) Integer.parseInt(tok, radix));
              } catch (NumberFormatException exp) {
                   sb.append(unknownCh);
              }
              i2++ ;
          }
          return sb.toString();
  }

   /**
    * Encode a String with Numeric Character Refernces.
    * <p>
    * Formats each character < 0x20 or > 0x7f to &#nnnn; where nnnn is the char value as int.
    * <p>
    *  
    * @param str The raw String
    * @return The encoded String
    */
   public static String encode( String str ) {
     char[] ch = str.toCharArray();
     StringBuffer sb = new StringBuffer();
     for ( int i = 0 ; i < ch.length ; i++ ) {
      if ( ch[i] < 0x20 || ch[i] > 0x7f )
        sb.append("&#").append((int) ch[i]).append(";");
      else
        sb.append(ch[i]);
     }
     return sb.toString();
   }
}

   
    
    
    
    
    
  