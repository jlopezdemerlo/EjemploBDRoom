package net.iessochoa.joseantoniolopez.ejemplobdroom.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;


import java.util.Date;
import java.util.List;

import io.reactivex.Single;

public class ContactoRepository {
    private ContactoDao mContactoDao;
    private LiveData<List<Contacto>> mAllContactos;

    public ContactoRepository(Application application){
        ContactoDatabase db=ContactoDatabase.getDatabase(application);
        mContactoDao =db.contactoDao();
        mAllContactos=mContactoDao.getAllContactos();
    }
    public LiveData<List<Contacto>> getAllContactos(){
        return mAllContactos;
    }
    public LiveData<List<Contacto>> getByNombre(String nombre){
        mAllContactos=mContactoDao.findByNombre(nombre);
        return mAllContactos;
    }


    public LiveData<List<Contacto>> getByNombreFecha(String nombre, Date menorQue){
        mAllContactos=mContactoDao.findByNombreFecha(nombre,menorQue);
        return mAllContactos;
    }
    //lista ordenado por columnas diferentes
    public LiveData<List<Contacto>> getContactosOrderByNombre(){
        mAllContactos=mContactoDao.getContactosOrderByNombre();
        return mAllContactos;
    }
    public LiveData<List<Contacto>> getContactosOrderByFecha(){
        mAllContactos=mContactoDao.getContactosOrderByFecha();
        return mAllContactos;
    }
    public LiveData<List<Contacto>> getContactosOrderBy(String order_by, String order){
        mAllContactos=mContactoDao.getContactosOrderBy(order_by, order);
        return mAllContactos;
    }
    public Single<Integer> geTotalContactos(){
        return mContactoDao.geTotalContactos();
    }
    /*
    Insertar: nos obliga a crear tarea en segundo plano
     */
    public void insert(Contacto contacto){
      //administramos el hilo con el Executor
        ContactoDatabase.databaseWriteExecutor.execute(()->{
            mContactoDao.insert(contacto);
        });


    }

/*
Borrar: nos obliga a crear tarea en segundo plano
 */
    public void delete(Contacto contacto){
        //administramos el hilo con el Executor
        ContactoDatabase.databaseWriteExecutor.execute(()->{
            mContactoDao.deleteByContacto(contacto);
        });
    }
    /*public void deleteById(int id){
        mContactoDao.deleteByContactoId(id);
    }*/
}
