package com.scd.app.service;

import com.scd.app.config.CommonConfig;
import com.scd.app.constant.Constant;
import com.scd.app.feign.FAccount;
import com.scd.app.pojo.vo.AccountInfoVo;
import com.scd.app.pojo.vo.CollectVo;
import com.scd.app.pojo.vo.MemberVo;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.constant.Type;
import com.scd.joggle.pojo.bo.AccountBalanceBo;
import com.scd.joggle.pojo.bo.AccountBo;
import com.scd.joggle.pojo.bo.CollectBo;
import com.scd.joggle.pojo.po.AccountPo;
import com.scd.joggle.pojo.po.MemberPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018-03-13.
 */
@Service
@Slf4j
public class AccountService {

    @Autowired
    private FAccount fAccount;

    public Return<AccountInfoVo> getInfo(String account) {
        if (account == null) {
            return Constant.createReturn(ErrorCom.ERROR);
        }
        AccountInfoVo accountInfoVo = null;
        AccountBo accountBo = fAccount.getInfoByAccount(account);
        if (accountBo != null) {
            accountInfoVo = new AccountInfoVo(
                    accountBo.getId(),
                    accountBo.getAccount(),
                    accountBo.getNickName(),
                    accountBo.getLoginName(),
                    accountBo.getHeadUrl(),
                    accountBo.getSex(),
                    "".equals(accountBo.getPayPassword())|| accountBo.getPayPassword() == null ? Type.NO_SET_PAY_PASSWORD:Type.IS_SET_PAY_PASSWORD,
                    accountBo.getIsPushMsg(),
                    accountBo.getBalance(),
                    accountBo.getYunBalance(),
                    accountBo.getLevel()
            );
        } else {
            return Constant.createReturn(ErrorCom.LOGIN_NAME_NOT_EXIST);
        }
        return Constant.createReturn(accountInfoVo);
    }

    /**
     * 获取推荐人列表
     * @param account
     * @return
     */
    public Return<PageInfo<AccountInfoVo>> getRefereeList(String account,int page,int size) {
        if (account == null) {
            return Constant.createReturn(ErrorCom.ERROR);
        }
        AccountBo accountBo = fAccount.getInfoByAccount(account);
        if(accountBo == null){
            return Constant.createReturn(ErrorCom.LOGIN_NAME_NOT_EXIST);
        }
        if(accountBo.getLevel() == Type.MEMBER_NORMAL){
            return Constant.createReturn(ErrorCom.NOT_REFEREE_POWER);
        }

        PageInfo<AccountInfoVo> kdPage = null;
        List<AccountInfoVo> accountInfoVoList = new ArrayList<>();
        Return<PageInfo<AccountBo>> refereeList = fAccount.getRefereeList(account, page, size);
        if(refereeList.getData() != null){
            List<AccountBo> listBo = refereeList.getData().getList();
            if(listBo.size() != 0){
                for(AccountBo accountBoList : listBo){
                    AccountInfoVo accountInfoVo = new AccountInfoVo(
                            accountBoList.getId(),
                            accountBoList.getAccount(),
                            accountBoList.getNickName(),
                            accountBoList.getLoginName(),
                            accountBoList.getHeadUrl(),
                            accountBoList.getSex(),
                            accountBoList.getLevel()
                    );
                    accountInfoVoList.add(accountInfoVo);
                }
            }
            kdPage= new PageInfo<>(refereeList.getData().getPage(),refereeList.getData().getTotal(),accountInfoVoList);
        }
        return Constant.createReturn(kdPage);
    }

    /**
     * 获取收藏列表
     * @param account
     * @param page
     * @param size
     * @return
     */
    public Return<PageInfo<CollectVo>> getCollectList(String account, int page, int size) {
        if (account == null) {
            return Constant.createReturn(ErrorCom.ERROR);
        }
        AccountBo accountBo = fAccount.getInfoByAccount(account);
        if(accountBo == null){
            return Constant.createReturn(ErrorCom.LOGIN_NAME_NOT_EXIST);
        }

        PageInfo<CollectVo> kdPage = null;
        Return<PageInfo<CollectBo>> list = fAccount.getCollectList(account, page, size);
        List<CollectVo> voList = new ArrayList<>();
        if(list.getData() != null){
            List<CollectBo> listBo = list.getData().getList();
            if(listBo.size() != 0){
                for(CollectBo collectBo : listBo){
                    CollectVo collectVo = new CollectVo(
                            collectBo.getId(),
                            collectBo.getAccountId(),
                            collectBo.getAccount(),
                            collectBo.getCommodityId(),
                            collectBo.getCreateTime(),
                            collectBo.getCommodityName(),
                            collectBo.getCommodityImg(),
                            collectBo.getPrice()
                    );
                    voList.add(collectVo);
                }
            }
            kdPage= new PageInfo<>(list.getData().getPage(),list.getData().getTotal(),voList);
        }
        return Constant.createReturn(kdPage);
    }

    public Return<Object> setPayPwd(String account, String payPwd) {
        AccountBo accountBo = fAccount.getInfoByAccount(account);
        if(accountBo == null){
            return Constant.createReturn(ErrorCom.LOGIN_NAME_NOT_EXIST);
        }
        boolean result = fAccount.setPayPwd(account,payPwd);
        if(!result){
            return Constant.createReturn(ErrorCom.SET_PAY_PASSWORD_ERROR);
        }
        return Constant.createReturn();
    }

    public Return<Object> updatePayPwd(String account, String payPwd, String oldPayPwd) {
        AccountBo accountBo = fAccount.getInfoByAccount(account);
        if(accountBo == null){
            return Constant.createReturn(ErrorCom.LOGIN_NAME_NOT_EXIST);
        }
        boolean result = fAccount.setPayPwd(account,payPwd);
        if(!result){
            return Constant.createReturn(ErrorCom.SET_PAY_PASSWORD_ERROR);
        }
        return Constant.createReturn();
    }


    public Return<AccountPo> updateAccountInfo(String account, String nickName, String headPic, int sex) {
        AccountBo accountBo = fAccount.getInfoByAccount(account);
        if(accountBo == null){
            return Constant.createReturn(ErrorCom.LOGIN_NAME_NOT_EXIST);
        }
        String pic = "";
        String nickNameStr = "";
        Return<AccountPo> accountPo = null;
        try {
            if(headPic != null){
                pic = headPic;
            }
            if(nickName != null){
                nickNameStr = nickName;
            }
            accountPo = fAccount.updateAccountInfo(account,nickNameStr,pic,sex);
            if(Return.isErr(accountPo)){
                return Constant.createReturn(ErrorCom.HEAD_UPLOAD_ERROR);
            }
        }catch (Exception e){
            log.error(e.getMessage());
            return Constant.createReturn(ErrorCom.HEAD_UPLOAD_ERROR);
        }
        return Constant.createReturn(accountPo.getData());
    }

	public Return<AccountInfoVo> getBalance(String account) {
		 if (account == null) {
	            return Constant.createReturn(ErrorCom.ERROR);
	        }
	        AccountInfoVo accountInfoVo = null;
	        AccountBalanceBo accountBo = fAccount.getAccountBalanceByAccount(account);
	        if (accountBo != null) {
	            accountInfoVo = new AccountInfoVo(accountBo.getBalance());
	        } else {
	            return Constant.createReturn(ErrorCom.LOGIN_NAME_NOT_EXIST);
	        }
	        return Constant.createReturn(accountInfoVo);
    }


	public Return<AccountInfoVo> getTotalEarnings(String account) {
		 if (account == null) {
	            return Constant.createReturn(ErrorCom.ERROR);
	        }
	        AccountInfoVo accountInfoVo = null;
	        AccountBalanceBo accountBo = fAccount.getAccountBalanceByAccount(account);
	        if (accountBo != null) {
	            accountInfoVo = new AccountInfoVo(accountBo.getAccount(),accountBo.getTotalEarnings());
	        } else {
	            return Constant.createReturn(ErrorCom.LOGIN_NAME_NOT_EXIST);
	        }
	        return Constant.createReturn(accountInfoVo);
    }


    public Return<Object> verifyOldPayPwd(String account, String oldPayPwd) {
        AccountBo accountBo = fAccount.getInfoByAccount(account);
        if(accountBo == null){
            return Constant.createReturn(ErrorCom.LOGIN_NAME_NOT_EXIST);
        }
        if(!oldPayPwd.equals(accountBo.getPayPassword())){
            return Constant.createReturn(ErrorCom.PASSWORD_ERROR_MATCH);
        }
        return Constant.createReturn();
    }

    public Return<MemberVo> myMember(String account) {
//        AccountBo accountBo = fAccount.getInfoByAccount(account);
//        if(accountBo == null){
//            return Constant.createReturn(ErrorCom.LOGIN_NAME_NOT_EXIST);
//        }
        Return<MemberPo> ret = fAccount.getMember(account);
        if(Return.isErr(ret)){
            return Constant.createReturn(ret.getCode(), ret.getDesc());
        }
        MemberPo po = ret.getData();

        int level = po.getLevel();
        BigDecimal totalConsume = po.getTotalConsume();
        BigDecimal totalRecharge = po.getTotalRecharge();

        String url = CommonConfig.getHtml5RegisterUrl();
        if(level == Type.MEMBER_VIP){
            url = url + "?&reffer=" + po.getAccount();
        }else{
            url = "";
        }
        MemberVo memberVo = new MemberVo(
        		level,
                url,
                totalConsume,
                totalRecharge
        );
        return Constant.createReturn(memberVo);
    }
}
