/* File:   TheNativeMethodImpl.c
 * Desc:   'C' implementation for the native method
 * Author: Rudolf Scheurer, HEIA-FR (rudolf.scheurer@hefr.ch)
 */

#include <stdio.h>
#include "AClassWithNativeMethods.h"

JNIEXPORT void JNICALL Java_AClassWithNativeMethods_theNativeMethod
	(JNIEnv* env, jobject thisObj) 	{
 	    printf("Hi folks, welcome to the secret world of JNI !\n");
}

