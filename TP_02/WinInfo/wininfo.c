/*******************************************************************
* Getting the Windows System Version
*
* The following code is based on an example originally
* provided at http://msdn.microsoft.com/
*
********************************************************************
* Contains various modifications by R. Scheurer (HEIA-FR)
********************************************************************/
#include <windows.h>
#include <tchar.h>
#include <stdio.h>
// modif R. Scheurer
// Work around lack of strsafe library in mingw-w64, do let their
// strsafe.h provide inlines of StringCopyWorkerA etc, avoid linking
// errors in a debug build.
#ifdef __CRT__NO_INLINE
#undef __CRT__NO_INLINE
#define DID_UNDEFINE__CRT__NO_INLINE
#endif
#include <strsafe.h>
#ifdef DID_UNDEFINE__CRT__NO_INLINE
#define __CRT__NO_INLINE
#endif
//#include "wininfo.h" // include needed for MinGW32 only

#define BUFSIZE 256
#define NT_SUCCESS(Status) ((NTSTATUS)(Status) >= 0)

typedef void (WINAPI *PGNSI)(LPSYSTEM_INFO);
typedef BOOL (WINAPI *PGPI)(DWORD, DWORD, DWORD, DWORD, PDWORD);
typedef NTSTATUS (NTAPI *PRGV)(RTL_OSVERSIONINFOEXW *);
		    
BOOL GetOSDisplayString( LPTSTR pszOS)
  { 
  // modif R. Scheurer
  TCHAR wininfo_type[BUFSIZE] = {0};
  TCHAR wininfo_edition[BUFSIZE] = {0};
  TCHAR wininfo_sp[40] = {0};
  TCHAR wininfo_build[40] = {0};
  TCHAR wininfo_arch[10] = {0};

  RTL_OSVERSIONINFOEXW osvi;
  SYSTEM_INFO si;
  PGNSI pGNSI;
  PGPI pGPI;
  PRGV pRGV;
  DWORD dwType;
  NTSTATUS res = -1;

  ZeroMemory(&si, sizeof(SYSTEM_INFO));
  ZeroMemory(&osvi, sizeof(RTL_OSVERSIONINFOEXW));
  osvi.dwOSVersionInfoSize = sizeof(RTL_OSVERSIONINFOEXW);

  // modif R. Scheurer
  // Call RtlGetVersion (instead of GetVersionEx)
  pRGV = (PRGV) GetProcAddress(
    GetModuleHandle(TEXT("ntdll.dll")),
    "RtlGetVersion");
  if (pRGV == NULL)
    return GetLastError();
  res = pRGV(&osvi);
  if (!NT_SUCCESS(res))
  {
    printf( "Sorry, unable to determine Windows version ...\n");
    return FALSE;
  }

  // Call GetNativeSystemInfo if supported or GetSystemInfo otherwise.
  pGNSI = (PGNSI) GetProcAddress(
    GetModuleHandle(TEXT("kernel32.dll")), 
    "GetNativeSystemInfo");
  if ( pGNSI != NULL )
    pGNSI(&si);
  else
    GetSystemInfo(&si);

  // Check for supported OS
  if ( VER_PLATFORM_WIN32_NT==osvi.dwPlatformId && osvi.dwMajorVersion > 4 )
  {
    StringCchCopy(wininfo_type, BUFSIZE, TEXT("Microsoft "));

    //--- Distinguish major OS types -------------------------------------------------

    if ( osvi.dwMajorVersion == 10 ) // modif R. Scheurer
    {
      if( osvi.dwMinorVersion == 0 )
      {
        if( osvi.wProductType == VER_NT_WORKSTATION )
        {
          StringCchCat(wininfo_type, BUFSIZE, TEXT("Windows 10"));
          // detect Win10 version based on build number
          if( osvi.dwBuildNumber == 10240) // modif R. Scheurer
            StringCchCat(wininfo_type, BUFSIZE, TEXT(" v1507"));
          else if( osvi.dwBuildNumber == 10586)
            StringCchCat(wininfo_type, BUFSIZE, TEXT(" v1511"));
          else if( osvi.dwBuildNumber == 14393)
            StringCchCat(wininfo_type, BUFSIZE, TEXT(" v1607"));
          else if( osvi.dwBuildNumber == 15063)
            StringCchCat(wininfo_type, BUFSIZE, TEXT(" v1703"));
        }
        else StringCchCat(wininfo_type, BUFSIZE, TEXT("Windows Server 2016" ));
      }
    }
    
    else if ( osvi.dwMajorVersion == 6 )
    {
      if( osvi.dwMinorVersion == 0 )
      {
        if( osvi.wProductType == VER_NT_WORKSTATION )
            StringCchCat(wininfo_type, BUFSIZE, TEXT("Windows Vista"));
        else StringCchCat(wininfo_type, BUFSIZE, TEXT("Windows Server 2008" ));
      }

      else if ( osvi.dwMinorVersion == 1 )
      {
        if( osvi.wProductType == VER_NT_WORKSTATION )
            StringCchCat(wininfo_type, BUFSIZE, TEXT("Windows 7"));
        else StringCchCat(wininfo_type, BUFSIZE, TEXT("Windows Server 2008 R2" ));
      }

      else if ( osvi.dwMinorVersion == 2 )  // modif R. Scheurer
      {
        if( osvi.wProductType == VER_NT_WORKSTATION )
            StringCchCat(wininfo_type, BUFSIZE, TEXT("Windows 8"));
        else StringCchCat(wininfo_type, BUFSIZE, TEXT("Windows Server 2012" ));
      }

      else if ( osvi.dwMinorVersion == 3 ) // modif R. Scheurer
      {
        if( osvi.wProductType == VER_NT_WORKSTATION )
            StringCchCat(wininfo_type, BUFSIZE, TEXT("Windows 8.1"));
        else StringCchCat(wininfo_type, BUFSIZE, TEXT("Windows Server 2012 R2" ));
      }

      else if ( osvi.dwMinorVersion == 4 ) // modif R. Scheurer (for some early builds of Win10)
      {
        if( osvi.wProductType == VER_NT_WORKSTATION )
            StringCchCat(wininfo_type, BUFSIZE, TEXT("Windows 10"));
        else StringCchCat(wininfo_type, BUFSIZE, TEXT("Windows Server 2016" ));
      }

      // --- Get OS edition ------------------------------------------------

      pGPI = (PGPI) GetProcAddress(
        GetModuleHandle(TEXT("kernel32.dll")), 
        "GetProductInfo");

      pGPI( osvi.dwMajorVersion, osvi.dwMinorVersion, 0, 0, &dwType);

      switch( dwType )
      {
        case PRODUCT_ULTIMATE:
           StringCchCopy(wininfo_edition, BUFSIZE, TEXT(" Ultimate Edition" ));
           break;
        case PRODUCT_PROFESSIONAL:
           StringCchCopy(wininfo_edition, BUFSIZE, TEXT(" Professional" ));
           break;
        case PRODUCT_HOME_PREMIUM:
           StringCchCopy(wininfo_edition, BUFSIZE, TEXT(" Home Premium Edition" ));
           break;
        case PRODUCT_HOME_BASIC:
           StringCchCopy(wininfo_edition, BUFSIZE, TEXT(" Home Basic Edition" ));
           break;
        case PRODUCT_ENTERPRISE:
           StringCchCopy(wininfo_edition, BUFSIZE, TEXT(" Enterprise Edition" ));
           break;
        case PRODUCT_BUSINESS:
           StringCchCopy(wininfo_edition, BUFSIZE, TEXT(" Business Edition" ));
           break;
        case PRODUCT_STARTER:
           StringCchCopy(wininfo_edition, BUFSIZE, TEXT(" Starter Edition" ));
           break;
        case PRODUCT_CLUSTER_SERVER:
           StringCchCopy(wininfo_edition, BUFSIZE, TEXT(" Cluster Server Edition" ));
           break;
        case PRODUCT_DATACENTER_SERVER:
           StringCchCopy(wininfo_edition, BUFSIZE, TEXT(" Datacenter Edition" ));
           break;
        case PRODUCT_DATACENTER_SERVER_CORE:
           StringCchCopy(wininfo_edition, BUFSIZE, TEXT(" Datacenter Edition (core installation)" ));
           break;
        case PRODUCT_ENTERPRISE_SERVER:
           StringCchCopy(wininfo_edition, BUFSIZE, TEXT(" Enterprise Edition" ));
           break;
        case PRODUCT_ENTERPRISE_SERVER_CORE:
           StringCchCopy(wininfo_edition, BUFSIZE, TEXT(" Enterprise Edition (core installation)" ));
           break;
        case PRODUCT_ENTERPRISE_SERVER_IA64:
           StringCchCopy(wininfo_edition, BUFSIZE, TEXT(" Enterprise Edition for Itanium-based Systems" ));
           break;
        case PRODUCT_SMALLBUSINESS_SERVER:
           StringCchCopy(wininfo_edition, BUFSIZE, TEXT(" Small Business Server" ));
           break;
        case PRODUCT_SMALLBUSINESS_SERVER_PREMIUM:
           StringCchCopy(wininfo_edition, BUFSIZE, TEXT(" Small Business Server Premium Edition" ));
           break;
        case PRODUCT_STANDARD_SERVER:
           StringCchCopy(wininfo_edition, BUFSIZE, TEXT(" Standard Edition" ));
           break;
        case PRODUCT_STANDARD_SERVER_CORE:
           StringCchCopy(wininfo_edition, BUFSIZE, TEXT(" Standard Edition (core installation)" ));
           break;
        case PRODUCT_WEB_SERVER:
           StringCchCopy(wininfo_edition, BUFSIZE, TEXT(" Web Server Edition" ));
           break;
        default:
           StringCchCopy(wininfo_edition, BUFSIZE, TEXT("" ));
      }
    } // dwMajorVersion == 6

    else if ( osvi.dwMajorVersion == 5 )
    {
      if ( osvi.dwMinorVersion == 2 )
      {
        if( GetSystemMetrics(SM_SERVERR2) )
          StringCchCat(wininfo_type, BUFSIZE, TEXT( "Windows Server 2003 R2,"));
        else if ( osvi.wSuiteMask==VER_SUITE_STORAGE_SERVER )
          StringCchCat(wininfo_type, BUFSIZE, TEXT( "Windows Storage Server 2003"));
        else if ( osvi.wSuiteMask==VER_SUITE_WH_SERVER )
          StringCchCat(wininfo_type, BUFSIZE, TEXT( "Windows Home Server"));
        else if( osvi.wProductType == VER_NT_WORKSTATION &&
                si.wProcessorArchitecture==PROCESSOR_ARCHITECTURE_AMD64)
        {
          StringCchCat(wininfo_type, BUFSIZE, TEXT( "Windows XP Professional x64 Edition"));
        }
        else StringCchCat(wininfo_type, BUFSIZE, TEXT("Windows Server 2003,"));

        // Test for the server type (edition)
        if ( osvi.wProductType != VER_NT_WORKSTATION )
        {
          if ( si.wProcessorArchitecture==PROCESSOR_ARCHITECTURE_IA64 )
          {
            if( osvi.wSuiteMask & VER_SUITE_DATACENTER )
               StringCchCopy(wininfo_edition, BUFSIZE, TEXT( " Datacenter Edition for Itanium-based Systems" ));
            else if( osvi.wSuiteMask & VER_SUITE_ENTERPRISE )
               StringCchCopy(wininfo_edition, BUFSIZE, TEXT( " Enterprise Edition for Itanium-based Systems" ));
          }

          else if ( si.wProcessorArchitecture==PROCESSOR_ARCHITECTURE_AMD64 )
          {
            if( osvi.wSuiteMask & VER_SUITE_DATACENTER )
               StringCchCopy(wininfo_edition, BUFSIZE, TEXT( " Datacenter x64 Edition" ));
            else if( osvi.wSuiteMask & VER_SUITE_ENTERPRISE )
               StringCchCopy(wininfo_edition, BUFSIZE, TEXT( " Enterprise x64 Edition" ));
            else StringCchCopy(wininfo_edition, BUFSIZE, TEXT( " Standard x64 Edition" ));
          }

          else
          {
            if ( osvi.wSuiteMask & VER_SUITE_COMPUTE_SERVER )
               StringCchCopy(wininfo_edition, BUFSIZE, TEXT( " Compute Cluster Edition" ));
            else if( osvi.wSuiteMask & VER_SUITE_DATACENTER )
               StringCchCopy(wininfo_edition, BUFSIZE, TEXT( " Datacenter Edition" ));
            else if( osvi.wSuiteMask & VER_SUITE_ENTERPRISE )
               StringCchCopy(wininfo_edition, BUFSIZE, TEXT( " Enterprise Edition" ));
            else if ( osvi.wSuiteMask & VER_SUITE_BLADE )
               StringCchCopy(wininfo_edition, BUFSIZE, TEXT( " Web Edition" ));
            else StringCchCopy(wininfo_edition, BUFSIZE, TEXT( " Standard Edition" ));
          }
        }
      }

      else if ( osvi.dwMinorVersion == 1 )
      {
        StringCchCat(wininfo_type, BUFSIZE, TEXT("Windows XP"));
        // edition
        if( osvi.wSuiteMask & VER_SUITE_PERSONAL )
          StringCchCopy(wininfo_edition, BUFSIZE, TEXT( " Home Edition" ));
        else StringCchCopy(wininfo_edition, BUFSIZE, TEXT( " Professional" ));
      }

      else if ( osvi.dwMinorVersion == 0 )
      {
        StringCchCat(wininfo_type, BUFSIZE, TEXT("Windows 2000"));
        // edition
        if ( osvi.wProductType == VER_NT_WORKSTATION )
        {
          StringCchCopy(wininfo_edition, BUFSIZE, TEXT( " Professional" ));
        }
        else 
        {
          if( osvi.wSuiteMask & VER_SUITE_DATACENTER )
             StringCchCopy(wininfo_edition, BUFSIZE, TEXT( " Datacenter Server" ));
          else if( osvi.wSuiteMask & VER_SUITE_ENTERPRISE )
             StringCchCopy(wininfo_edition, BUFSIZE, TEXT( " Advanced Server" ));
          else StringCchCopy(wininfo_edition, BUFSIZE, TEXT( " Server" ));
        }
      }
    } // dwMajorVersion == 5
    
    //--- Detect service pack, build number and architecture -------------------------

    // service pack (if any) 
    if( osvi.wServicePackMajor > 0 )
      StringCchPrintf(wininfo_sp, 80, TEXT(", Service Pack %d"), osvi.wServicePackMajor);
    else
      StringCchCopy(wininfo_sp, 80, TEXT( "" ));

    // build number
    StringCchPrintf(wininfo_build, 80, TEXT(", Build %d"), osvi.dwBuildNumber);

    // architecture
    if ( osvi.dwMajorVersion >= 6 )
    {
      if ( si.wProcessorArchitecture==PROCESSOR_ARCHITECTURE_AMD64 )
        StringCchCopy(wininfo_arch, 80, TEXT( " (64-bit)" ));
      else if (si.wProcessorArchitecture==PROCESSOR_ARCHITECTURE_INTEL )
        StringCchCopy(wininfo_arch, 80, TEXT( " (32-bit)" ));
    }
    else
    {
      StringCchCat(wininfo_arch, 80, TEXT( "" ));
    }
    
    StringCchPrintf(pszOS, BUFSIZE, TEXT("%s%s%s%s%s\n"),
      wininfo_type, wininfo_edition, wininfo_arch, wininfo_sp, wininfo_build);
      
    return TRUE;
  }

  else
  {  
    printf( "Sorry, unknown (old?) Windows version ...\n");
    return FALSE;
  }
}

int __cdecl _tmain()
{
  TCHAR szOS[BUFSIZE];

  if( GetOSDisplayString( szOS ) )
    _tprintf( TEXT("\n%s\n"), szOS );
}
