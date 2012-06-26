/* Copyright (c) 2012, Jeffrey Dileo
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

import java.security.MessageDigest;

import java.nio.charset.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
public class TripGen{

	public static void main(String[] args){
		for(int a = 0; a < args.length; a++){
			String trip;
			if(args[a].indexOf('#') != -1){
				trip = args[a].substring(args[a].indexOf('#')+1, args[a].length());
			} else{
				trip = args[a];
			}
			byte[] trip2 = {};
			try{
				Charset charset = Charset.forName("Shift_JIS");
				CharsetEncoder encoder = charset.newEncoder();
				CharsetDecoder decoder = charset.newDecoder();
				ByteBuffer bbuf = encoder.encode(CharBuffer.wrap(trip));
				CharBuffer cbuf = decoder.decode(bbuf);
				trip2 = bbuf.array();
			} catch (Exception e) {
				e.printStackTrace();
			}

			String salt = (trip + "H.").substring(1,3);
			salt = salt.replaceAll("[^\\.-z]", ".");
			String from = ":;<=>?@[\\]^_`";
			String to = "ABCDEFGabcdef";
			for(int i = 0; i < from.length(); i++ ){
				salt = salt.replace(from.charAt(i), to.charAt(i));
			}
			String hash = "";
			try{
				hash = Crypt.crypt(salt.getBytes("UTF-8"), trip2).substring(3,13);
			} catch (Exception e){
				e.printStackTrace();
			}
			System.out.println("#" + trip + ":" + "♦" + hash);
		}
	}
}
