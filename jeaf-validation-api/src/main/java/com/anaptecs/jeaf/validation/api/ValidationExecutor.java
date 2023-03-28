package com.anaptecs.jeaf.validation.api;

/**
 * Interface defines a very light-weight approach to request and response validation in RESt environments. Through this
 * interface generated REST Resource / Controllers can trigger request and response validation very easily.
 * 
 * @author JEAF Development Team
 */
public interface ValidationExecutor {
  /**
   * Method validates the passed request objects. It is expected that implementations throws a runtime exception that
   * describes all the validations that failed.
   * 
   * @param pService Class object representing the service to which the passed request object belongs to. The parameter
   * must not be null.
   * @param pRequestParameters All request parameters that should be validated. The parameter may be null. Please be
   * aware that <code>pRequestParameters</code> might contain a mixture of different types including Java collections
   * classes, primitive arrays etc.
   */
  void validateRequest( Class<?> pService, Object... pRequestParameters );

  /**
   * Method validates the passed response object. It is expected that implementations throws a runtime exception that
   * describes all the validations that failed.
   * 
   * @param pService Class object representing the service to which the passed request object belongs to. The parameter
   * must not be null.
   * @param pResponseObject Response object that should be validated. The parameter may be null.
   */
  void validateResponse( Class<?> pService, Object pResponseObject );

  /**
   * Method validates the passed object. It is expected that implementations throws a runtime exception that describes
   * all the validations that failed.
   * 
   * @param pObject Object that should be validated. The parameter may be null.
   */
  void validateObject( Object pObject );
}
