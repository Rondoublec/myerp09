package com.dummy.myerp.consumer.dao.impl.db.dao;

import java.sql.Types;
import java.util.List;

import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.SequenceEcritureComptableRM;
import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.CompteComptableRM;
import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.EcritureComptableRM;
import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.JournalComptableRM;
import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.LigneEcritureComptableRM;
import com.dummy.myerp.consumer.db.AbstractDbConsumer;
import com.dummy.myerp.consumer.db.DataSourcesEnum;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.NotFoundException;

import static com.dummy.myerp.consumer.utils.Constants.*;

/**
 * Implémentation de l'interface {@link ComptabiliteDao}
 */
public class ComptabiliteDaoImpl extends AbstractDbConsumer implements ComptabiliteDao {

    // ==================== Constructeurs ====================
    /** Instance unique de la classe (design pattern Singleton) */
    private static final ComptabiliteDaoImpl INSTANCE = new ComptabiliteDaoImpl();
    private static String SQLgetListCompteComptable;
    private static String sqlSelectSequenceEcritureComptable;
    private static String sqlInsertSequenceEcritureComptable;
    private static String sqlUpdateSequenceEcritureComptable;

    /**
     * Constructeur.
     */
    protected ComptabiliteDaoImpl() {
        super();
    }

    /**
     * Renvoie l'instance unique de la classe (design pattern Singleton).
     *
     * @return {@link ComptabiliteDaoImpl}
     */
    public static ComptabiliteDaoImpl getInstance() {
        return ComptabiliteDaoImpl.INSTANCE;
    }

    // ==================== Méthodes ====================
    /** SQLgetListCompteComptable */
    public void setSQLgetListCompteComptable(String pSQLgetListCompteComptable) {
        SQLgetListCompteComptable = pSQLgetListCompteComptable;
    }

    public void setSqlUpdateSequenceEcritureComptable(String pSqlUpdateSequenceEcritureComptable) {
        sqlUpdateSequenceEcritureComptable = pSqlUpdateSequenceEcritureComptable;
    }
    public void setSqlInsertSequenceEcritureComptable(String pSqlInsertSequenceEcritureComptable) {
        sqlInsertSequenceEcritureComptable = pSqlInsertSequenceEcritureComptable;
    }
    public void setSqlSelectSequenceEcritureComptable(String pSqlSelectSequenceEcritureComptable) {
        sqlSelectSequenceEcritureComptable = pSqlSelectSequenceEcritureComptable;
    }

    @Override
    public Integer getDerniereValeurSequenceEcriture(String journalCode, Integer annee) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue(JOURNAL_CODE, journalCode);
        sqlParameterSource.addValue(ANNEE, annee);
        SequenceEcritureComptableRM rowMapper = new SequenceEcritureComptableRM();
        List<SequenceEcritureComptable> sequenceEcritureComptableList = jdbcTemplate.query(sqlSelectSequenceEcritureComptable, sqlParameterSource, rowMapper);
        if (sequenceEcritureComptableList.isEmpty()) {
            return null;
        } else {
            return sequenceEcritureComptableList.get(0).getDerniereValeur();
        }
    }

    @Override
    public void insertSequenceEcriture(SequenceEcritureComptable sequenceEcritureComptable) {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(JOURNAL_CODE, sequenceEcritureComptable.getJournalCode());
        vSqlParams.addValue(ANNEE, sequenceEcritureComptable.getAnnee());
        vSqlParams.addValue(DERNIERE_VALEUR, sequenceEcritureComptable.getDerniereValeur());
        vJdbcTemplate.update(sqlInsertSequenceEcritureComptable, vSqlParams);

    }

    @Override
    public void updateSequenceEcriture(SequenceEcritureComptable sequenceEcritureComptable) {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(JOURNAL_CODE, sequenceEcritureComptable.getJournalCode());
        vSqlParams.addValue(ANNEE, sequenceEcritureComptable.getAnnee());
        vSqlParams.addValue(DERNIERE_VALEUR, sequenceEcritureComptable.getDerniereValeur());
        vJdbcTemplate.update(sqlUpdateSequenceEcritureComptable, vSqlParams);

    }

    @Override
    public List<CompteComptable> getListCompteComptable() {
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(this.getDataSource(DataSourcesEnum.MYERP));
        CompteComptableRM vRM = new CompteComptableRM();
        List<CompteComptable> vList = vJdbcTemplate.query(SQLgetListCompteComptable, vRM);
        return vList;
    }


    /** SQLgetListJournalComptable */
    private static String SQLgetListJournalComptable;
    public void setSQLgetListJournalComptable(String pSQLgetListJournalComptable) {
        SQLgetListJournalComptable = pSQLgetListJournalComptable;
    }
    @Override
    public List<JournalComptable> getListJournalComptable() {
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(this.getDataSource(DataSourcesEnum.MYERP));
        JournalComptableRM vRM = new JournalComptableRM();
        List<JournalComptable> vList = vJdbcTemplate.query(SQLgetListJournalComptable, vRM);
        return vList;
    }

    // ==================== EcritureComptable - GET ====================

    /** SQLgetListEcritureComptable */
    private static String SQLgetListEcritureComptable;
    public void setSQLgetListEcritureComptable(String pSQLgetListEcritureComptable) {
        SQLgetListEcritureComptable = pSQLgetListEcritureComptable;
    }
    @Override
    public List<EcritureComptable> getListEcritureComptable() {
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(this.getDataSource(DataSourcesEnum.MYERP));
        EcritureComptableRM vRM = new EcritureComptableRM();
        List<EcritureComptable> vList = vJdbcTemplate.query(SQLgetListEcritureComptable, vRM);
        return vList;
    }


    /** SQLgetEcritureComptable */
    private static String SQLgetEcritureComptable;
    public void setSQLgetEcritureComptable(String pSQLgetEcritureComptable) {
        SQLgetEcritureComptable = pSQLgetEcritureComptable;
    }
    @Override
    public EcritureComptable getEcritureComptable(Integer pId) throws NotFoundException {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(ID, pId);
        EcritureComptableRM vRM = new EcritureComptableRM();
        EcritureComptable vBean;
        try {
            vBean = vJdbcTemplate.queryForObject(SQLgetEcritureComptable, vSqlParams, vRM);
        } catch (EmptyResultDataAccessException vEx) {
            throw new NotFoundException("EcritureComptable non trouvée : id=" + pId);
        }
        return vBean;
    }


    /** SQLgetEcritureComptableByRef */
    private static String SQLgetEcritureComptableByRef;
    public void setSQLgetEcritureComptableByRef(String pSQLgetEcritureComptableByRef) {
        SQLgetEcritureComptableByRef = pSQLgetEcritureComptableByRef;
    }
    @Override
    public EcritureComptable getEcritureComptableByRef(String pReference) throws NotFoundException {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(REFERENCE, pReference);
        EcritureComptableRM vRM = new EcritureComptableRM();
        EcritureComptable vBean;
        try {
            vBean = vJdbcTemplate.queryForObject(SQLgetEcritureComptableByRef, vSqlParams, vRM);
        } catch (EmptyResultDataAccessException vEx) {
            throw new NotFoundException("EcritureComptable non trouvée : reference=" + pReference);
        }
        return vBean;
    }


    /** SQLloadListLigneEcriture */
    private static String SQLloadListLigneEcriture;
    public void setSQLloadListLigneEcriture(String pSQLloadListLigneEcriture) {
        SQLloadListLigneEcriture = pSQLloadListLigneEcriture;
    }
    @Override
    public void loadListLigneEcriture(EcritureComptable pEcritureComptable) {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(ECRITURE_ID, pEcritureComptable.getId());
        LigneEcritureComptableRM vRM = new LigneEcritureComptableRM();
        List<LigneEcritureComptable> vList = vJdbcTemplate.query(SQLloadListLigneEcriture, vSqlParams, vRM);
        pEcritureComptable.getListLigneEcriture().clear();
        pEcritureComptable.getListLigneEcriture().addAll(vList);
    }


    // ==================== EcritureComptable - INSERT ====================

    /** SQLinsertEcritureComptable */
    private static String SQLinsertEcritureComptable;
    public void setSQLinsertEcritureComptable(String pSQLinsertEcritureComptable) {
        SQLinsertEcritureComptable = pSQLinsertEcritureComptable;
    }
    @Override
    public void insertEcritureComptable(EcritureComptable pEcritureComptable) {
        // ===== Ecriture Comptable
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(JOURNAL_CODE, pEcritureComptable.getJournal().getCode());
        vSqlParams.addValue(REFERENCE, pEcritureComptable.getReference());
        vSqlParams.addValue(DATE, pEcritureComptable.getDate(), Types.DATE);
        vSqlParams.addValue(LIBELLE, pEcritureComptable.getLibelle());

        vJdbcTemplate.update(SQLinsertEcritureComptable, vSqlParams);

        // ----- Récupération de l'id
        Integer vId = this.queryGetSequenceValuePostgreSQL(DataSourcesEnum.MYERP, "myerp.ecriture_comptable_id_seq",
                                                           Integer.class);
        pEcritureComptable.setId(vId);

        // ===== Liste des lignes d'écriture
        this.insertListLigneEcritureComptable(pEcritureComptable);
    }

    /** SQLinsertListLigneEcritureComptable */
    private static String SQLinsertListLigneEcritureComptable;
    public void setSQLinsertListLigneEcritureComptable(String pSQLinsertListLigneEcritureComptable) {
        SQLinsertListLigneEcritureComptable = pSQLinsertListLigneEcritureComptable;
    }
    /**
     * Insert les lignes d'écriture de l'écriture comptable
     * @param pEcritureComptable l'écriture comptable
     */
    protected void insertListLigneEcritureComptable(EcritureComptable pEcritureComptable) {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(ECRITURE_ID, pEcritureComptable.getId());

        int vLigneId = 0;
        for (LigneEcritureComptable vLigne : pEcritureComptable.getListLigneEcriture()) {
            vLigneId++;
            vSqlParams.addValue(LIGNE_ID, vLigneId);
            vSqlParams.addValue(COMPTE_COMPTABLE_NUMERO, vLigne.getCompteComptable().getNumero());
            vSqlParams.addValue(LIBELLE, vLigne.getLibelle());
            vSqlParams.addValue(DEBIT, vLigne.getDebit());

            vSqlParams.addValue(CREDIT, vLigne.getCredit());

            vJdbcTemplate.update(SQLinsertListLigneEcritureComptable, vSqlParams);
        }
    }


    // ==================== EcritureComptable - UPDATE ====================

    /** SQLupdateEcritureComptable */
    private static String SQLupdateEcritureComptable;
    public void setSQLupdateEcritureComptable(String pSQLupdateEcritureComptable) {
        SQLupdateEcritureComptable = pSQLupdateEcritureComptable;
    }
    @Override
    public void updateEcritureComptable(EcritureComptable pEcritureComptable) {
        // ===== Ecriture Comptable
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(ID, pEcritureComptable.getId());
        vSqlParams.addValue(JOURNAL_CODE, pEcritureComptable.getJournal().getCode());
        vSqlParams.addValue(REFERENCE, pEcritureComptable.getReference());
        vSqlParams.addValue(DATE, pEcritureComptable.getDate(), Types.DATE);
        vSqlParams.addValue(LIBELLE, pEcritureComptable.getLibelle());

        vJdbcTemplate.update(SQLupdateEcritureComptable, vSqlParams);

        // ===== Liste des lignes d'écriture
        this.deleteListLigneEcritureComptable(pEcritureComptable.getId());
        this.insertListLigneEcritureComptable(pEcritureComptable);
    }


    // ==================== EcritureComptable - DELETE ====================

    /** SQLdeleteEcritureComptable */
    private static String SQLdeleteEcritureComptable;
    public void setSQLdeleteEcritureComptable(String pSQLdeleteEcritureComptable) {
        SQLdeleteEcritureComptable = pSQLdeleteEcritureComptable;
    }
    @Override
    public void deleteEcritureComptable(Integer pId) {
        // ===== Suppression des lignes d'écriture
        this.deleteListLigneEcritureComptable(pId);

        // ===== Suppression de l'écriture
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(ID, pId);
        vJdbcTemplate.update(SQLdeleteEcritureComptable, vSqlParams);
    }

    /** SQLdeleteListLigneEcritureComptable */
    private static String SQLdeleteListLigneEcritureComptable;
    public void setSQLdeleteListLigneEcritureComptable(String pSQLdeleteListLigneEcritureComptable) {
        SQLdeleteListLigneEcritureComptable = pSQLdeleteListLigneEcritureComptable;
    }
    /**
     * Supprime les lignes d'écriture de l'écriture comptable d'id {@code pEcritureId}
     * @param pEcritureId id de l'écriture comptable
     */
    protected void deleteListLigneEcritureComptable(Integer pEcritureId) {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(ECRITURE_ID, pEcritureId);
        vJdbcTemplate.update(SQLdeleteListLigneEcritureComptable, vSqlParams);
    }
}
