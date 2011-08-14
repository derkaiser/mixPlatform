package ifaceloading;

/**
 * Example loader Method
 */

import java.io.*;
import java.lang.reflect.Method;
import java.net.*;

import architectureInterface.OutputStrategyInterface;

public class OutputStrategyLoader {
	public static OutputStrategyInterface getClass( String extFilePath, String classname ) 
	{
		System.out.println("Starting to load external class...");
		//für Rückgabe		
		OutputStrategyInterface extIF = null;

		try {
			final URL url = new File( extFilePath ).toURI().toURL();

			System.out.println("URL to load from: " + url);
			URLClassLoader ucl = new URLClassLoader( new URL[] { url } );
			//Klasse laden
			final Class<?> tempClass = ucl.loadClass( classname );

			//für unbekannte Klassen: Ausgabe alles Methodensignaturen
			Method m[] = tempClass.getDeclaredMethods();
			for (int i = 0; i < m.length; i++)
				System.out.println(m[i].toString());

			//Test, ob Klasse das Interface implementiert
			if (OutputStrategyInterface.class.isAssignableFrom( tempClass )) {

				//Cast und direkte Benutzung des Interfaces
				extIF = (OutputStrategyInterface) tempClass.newInstance();
			}	
		}
		catch( MalformedURLException ex ) {
			// catch the exception
			System.out.println("MalformedURLException: " + ex.getMessage());
		}
		catch( ClassNotFoundException ex ) {
			// catch the exception
			System.out.println("ClassNotFoundException: " + ex.getMessage());
		}
		catch( ClassCastException ex ) {
			// catch the exception
			System.out.println("ClassCastException: " + ex.getMessage());
		}
		catch( InstantiationException ex ) {
			// catch the exception
			System.out.println("InstantiationException: " + ex.getMessage());
		}
		catch( IllegalAccessException ex ) {
			// catch the exception
			System.out.println("IllegalAccessException: " + ex.getMessage());
		} catch (SecurityException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		} catch (IllegalArgumentException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		return extIF;
	}
}