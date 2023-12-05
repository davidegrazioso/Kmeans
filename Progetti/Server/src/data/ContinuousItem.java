package data;
/**
 * ContinuousItem:
 * classe derivata da Item. modella una coppia attributo continuo - valore
 */
public class ContinuousItem extends Item{

/**
 * costruttore della classe. richiama il costruttore della super classe
 * @param attribute : valore da dare all' attributo Attribute
 * @param value : valore da dare all'attributo Value
 */
    ContinuousItem(Attribute attribute, double value) {
        super(attribute, value);
        

}

/**
 *Determina la distanza (in valore assoluto) tra il valore 
 *scalato memorizzato nello item corrente  e quello scalato 
 *associato al parametro a. 
 *@param a : valore da usare per la differenza
 *@return distanza  tra il valore scalato memorizzato nello item corrente e quello scalato associato al parametro a
 */
  @Override
    public double distance(Object a) {
        return Math.abs(((ContinuousAttribute)this.getAttribute()).getScaledValue(((double)this.getValue())) - ((ContinuousAttribute)this.getAttribute()).getScaledValue(((double)a)) );
    }

}
