
    @Override
    public DBSCoreServiceResultDTO<List<DBSInstitutionInfoDTO>> getDBSInstitutionList() throws HmnServiceException {
        DBSCoreServiceResultDTO<List<DBSInstitutionInfoDTO>> resultDto = new DBSCoreServiceResultDTO<List<DBSInstitutionInfoDTO>>();
        resultDto.setResultDataDTO(QuerySupport.query("GET_DBS_INSTITUTION_LIST", new GetDBSInstitutionListTransformer()));
        return resultDto;
    }

    @Override
    public DBSCoreServiceResultDTO<List<DBSSubscriberInfoDTO>> getDBSSubscriberListByInstitutionAndCustomerNo(String institution, Integer customerNo)
            throws HmnServiceException {
        DBSCoreServiceResultDTO<List<DBSSubscriberInfoDTO>> resultDto = new DBSCoreServiceResultDTO<List<DBSSubscriberInfoDTO>>();
        resultDto.setResultDataDTO(QuerySupport.query("GET_SUBSCRIBER_LIST_BY_CLIENT_NO", new GetSubscriberListByClientNoTransformer("DBS", institution, customerNo)));
        return resultDto;
    }

    @Override
    public DBSCoreServiceResultDTO<List<DBSSubscriberInstitutionInfoDTO>> getDBSSubscriberInstitutionInfo(Integer customerNo) throws HmnServiceException {
        DBSCoreServiceResultDTO<List<DBSSubscriberInstitutionInfoDTO>> resultDto = new DBSCoreServiceResultDTO<List<DBSSubscriberInstitutionInfoDTO>>();
        resultDto.setResultDataDTO(QuerySupport.query(NQConsts.GET_SUBSCRIBER_INSTITUTION_INFO.QUERY_NAME, new GetSubscriberInstitutionInfoTransformer("DBS", customerNo)));
        return resultDto;
    }




    public class GetSubscriberInstitutionInfoTransformer implements QueryTransformer<DBSSubscriberInstitutionInfoDTO> {
    
    private String  product;
    private Integer clientNo;
    
    public GetSubscriberInstitutionInfoTransformer(String product, Integer clientNo) {
        this.product = product;
        this.clientNo = clientNo;
    }

    @Override
    public DBSSubscriberInstitutionInfoDTO convert(Map<String, Object> map) throws HmnServiceException {
        DBSSubscriberInstitutionInfoDTO dbsSubscriberInstitutionInfoDTO = new DBSSubscriberInstitutionInfoDTO();
        dbsSubscriberInstitutionInfoDTO.setInstitutionName(ObjectUtils.objToTargetType(map.get(GET_SUBSCRIBER_INSTITUTION_INFO.OUT_INSTITUTIONNAME), String.class));
        dbsSubscriberInstitutionInfoDTO.setSecondaryAccountNo(ObjectUtils.objToTargetType(map.get(GET_SUBSCRIBER_INSTITUTION_INFO.OUT_SECONDARYACCOUNTNO), String.class));
        return dbsSubscriberInstitutionInfoDTO;
    }

    @Override
    public Map<String, Object> prepare() {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(GET_SUBSCRIBER_INSTITUTION_INFO.IN_PRODUCT, product);
        parameters.put(GET_SUBSCRIBER_INSTITUTION_INFO.IN_CUSTOMERNO, clientNo);
        return parameters;
    }
