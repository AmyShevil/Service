/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.Aisova.api;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import ru.sfedu.Aisova.Bean1;
import ru.sfedu.Aisova.utils.ConfigurationUtil;
import ru.sfedu.Aisova.utils.WrapperXML;

/**
 *
 * @author sp2
 */
public class DataProviderXMLOld {
    
    private final String PATH="xml_path";
    private final String FILE_EXTENTION="xml";
    
    private static Logger log = LogManager.getLogger(DataProviderXMLOld.class);
    
    public void insertBean1(List<Bean1> bean1List) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, Exception{
        try{
            //Проверяем, создан ли файл? Если нет, то создаём.
            //createNewFile возвращает истину, если путь к абстрактному файлу не существует и создается новый файл.
            // Он возвращает false, если имя файла уже существует.
            (new File(this.getFilePath(Bean1.class))).createNewFile();
            //Подключаемся к потоку записи файла
            FileWriter writer = new FileWriter(this.getFilePath(Bean1.class), false);
            //Определяем сериалайзер
            Serializer serializer = new Persister();
            
            //Определяем контейнер, в котором будут находиться все объекты
            WrapperXML<Bean1> xml = new WrapperXML<Bean1>();
            //Записываем список объектов в котнейнер
            xml.setList(bean1List);
            
            //Записываем в файл
            serializer.write(xml, writer);
        }catch(IndexOutOfBoundsException e){
            log.error(e);
        }
    }
    
    public Bean1 getBean1ById(long id) throws IOException, Exception{
        List<Bean1> list = this.select(Bean1.class);
        try{
            Bean1 bean1=list.stream()
                    .filter(el->el.getId()==id)
                    .limit(1)
                    .findFirst().get();
            return bean1;
        }catch(NoSuchElementException e){
            log.error(e);
            return null;
        }
    }
    
    public <T> List<T> select(Class cl) throws IOException, Exception{
        //Подключаемся к считывающему потоку из файла
        FileReader fileReader = new FileReader(this.getFilePath(cl));
        //Определяем сериалайзер
        Serializer serializer = new Persister();
        //Определяем контейнер и записываем в него объекты
        WrapperXML xml = serializer.read(WrapperXML.class, fileReader);
        //Если список null, то делаем его пустым списком
        if (xml.getList() == null) xml.setList(new ArrayList<T>());
        //Возвращаем список объектов
        return xml.getList();
    }
    
    /**
     * Получаем путь к файлу
     * @param cl
     * @return
     * @throws IOException 
     */
    private String getFilePath(Class cl) throws IOException{
        return ConfigurationUtil.getConfigurationEntry(PATH)+cl.getSimpleName().toString().toLowerCase()+ConfigurationUtil.getConfigurationEntry(FILE_EXTENTION);
    }
}
