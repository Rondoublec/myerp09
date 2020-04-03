package com.dummy.myerp.business.impl;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * <p>Classe de gestion des Transactions de persistance</p>
 */
public class TransactionManager {

    // ==================== Constructeurs ====================
    /** Instance unique de la classe (design pattern Singleton) */
    private static final TransactionManager INSTANCE = new TransactionManager();
    // ==================== Attributs Static ====================
    /** PlatformTransactionManager pour le DataSource MyERP */
    private static PlatformTransactionManager platformTransactionManager;
    /**
     * Constructeur.
     */
    protected TransactionManager() {
        super();
    }
    /**
     * Renvoie l'instance unique de la classe (design pattern Singleton).
     *
     * @return {@link TransactionManager}
     */
    public static TransactionManager getInstance() {
        return TransactionManager.INSTANCE;
    }
    /**
     * Renvoie l'instance unique de la classe (design pattern Singleton).
     *
     * @param transactionManager -
     * @return {@link TransactionManager}
     */
    public static TransactionManager getInstance(PlatformTransactionManager transactionManager) {
        TransactionManager.platformTransactionManager = transactionManager;
        return TransactionManager.INSTANCE;
    }


    // ==================== Méthodes ====================
    /**
     * Démarre une transaction sur le DataSource MyERP
     *
     * @return TransactionStatus à passer aux méthodes :
     *      <ul>
     *          <li>{@link #commitMyERP(TransactionStatus)}</li>
     *              <li>{@link #rollbackMyERP(TransactionStatus)}</li>
     *      </ul>
     */
    public TransactionStatus beginTransactionMyERP() {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setName("Transaction_txManagerMyERP");
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        return platformTransactionManager.getTransaction(defaultTransactionDefinition);
    }

    /**
     * Commit la transaction sur le DataSource MyERP
     *
     * @param transactionStatus retrouné par la méthode {@link #beginTransactionMyERP()}
     */
    public void commitMyERP(TransactionStatus transactionStatus) {
        if (transactionStatus != null) {
            platformTransactionManager.commit(transactionStatus);
        }
    }

    /**
     * Rollback la transaction sur le DataSource MyERP
     *
     * @param transactionStatus retrouné par la méthode {@link #beginTransactionMyERP()}
     */
    public void rollbackMyERP(TransactionStatus transactionStatus) {
        if (transactionStatus != null) {
            platformTransactionManager.rollback(transactionStatus);
        }
    }
}
