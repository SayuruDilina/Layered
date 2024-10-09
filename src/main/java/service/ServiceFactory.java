package service;

import service.custom.Impl.CustomerServiceImpl;
import service.custom.Impl.ItemServiceImpl;
import util.ServiceType;

public class ServiceFactory {

    private static ServiceFactory instance;

    private ServiceFactory(){}


    public static ServiceFactory getInstance() {
        return instance==null?instance=new ServiceFactory():instance;
    }
public <T extends  SuperService>T getServiceType(ServiceType type){
        switch(type){
            case CUSTOMER:return (T) new CustomerServiceImpl();
            case ITEM:return  (T) new ItemServiceImpl();
        }
        return null;
}

}
