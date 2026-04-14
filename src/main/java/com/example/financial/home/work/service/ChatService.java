package com.example.financial.home.work.service;

import com.example.financial.home.work.dto.ChatRequest;
import com.example.financial.home.work.dto.ChatResponse;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ChatService {

    private static final Map<String, String> MOCK_ANSWERS = Map.ofEntries(
        Map.entry("基金", "基金是一种集合投资工具，由基金管理公司汇集众多投资者的资金，统一投资于股票、债券等金融产品。常见类型：\n• 股票型基金：主要投资股票，收益潜力大，风险较高\n• 债券型基金：主要投资债券，收益稳定，风险较低\n• 指数基金（ETF）：跟踪特定指数，费率低，适合长期投资\n• 货币基金：类似活期存款，流动性强，收益略高于银行利息\n\n建议新手从指数基金开始，采用定投方式分散风险。"),
        Map.entry("股票", "股票代表公司的所有权份额。买入股票即成为该公司股东，享有分红权和投票权。\n\n投资股票需关注：\n• 市盈率（PE）：衡量估值高低，一般低于20倍较合理\n• 市净率（PB）：适合银行等重资产行业估值\n• ROE（净资产收益率）：越高说明公司盈利能力越强\n• 行业景气度与公司基本面\n\n建议：分散持股，不要把所有资金投入单只股票。"),
        Map.entry("债券", "债券是一种固定收益证券，发行方（政府或企业）借款并承诺定期支付利息，到期还本。\n\n主要类型：\n• 国债：风险最低，收益稳定，免税\n• 地方政府债：风险较低，收益略高于国债\n• 企业债/公司债：风险高于政府债，收益也更高\n• 可转债：兼具债券和股票特性，可转换为股票\n\n债券价格与利率反向运动：利率上升时，债券价格下跌。"),
        Map.entry("市盈率", "市盈率（PE，Price-Earnings Ratio）= 股价 ÷ 每股收益\n\n简单来说，市盈率表示投资者为每1元利润愿意支付多少钱。\n\n• PE < 15：估值偏低，可能被低估\n• PE 15~25：估值合理区间\n• PE > 40：估值偏高，需谨慎\n\n注意：不同行业PE差异很大，科技公司通常PE较高，银行股PE较低，需横向对比同行业。"),
        Map.entry("指数基金", "指数基金是跟踪特定股票指数（如沪深300、标普500）的基金，被动管理，不主动选股。\n\n优点：\n• 费率低：管理费通常仅0.1%~0.5%\n• 分散风险：一键投资数百只股票\n• 长期收益稳定：跑赢大多数主动基金\n• 透明度高：持仓与指数一致\n\n推荐定投策略：每月固定金额买入，平摊成本，长期持有。巴菲特也推荐普通人购买指数基金。"),
        Map.entry("A股", "A股是指在中国大陆上海证券交易所和深圳证券交易所上市的人民币普通股票。\n\n特点：\n• 交易时间：周一至周五 9:30-11:30、13:00-15:00\n• 涨跌停限制：主板±10%，科创板/创业板±20%\n• T+1制度：今天买入明天才能卖出\n• 以散户为主，情绪波动较大\n\nA股 vs 港股：港股无涨跌停限制，T+0交易，以机构投资者为主，整体估值相对较低。"),
        Map.entry("分散", "资产分散配置是降低投资风险的核心策略，遵循「不把所有鸡蛋放在同一个篮子」原则。\n\n常见分散维度：\n• 资产类别：股票 + 债券 + 现金 + 黄金\n• 地域：A股 + 港股 + 美股\n• 行业：科技 + 消费 + 医疗 + 金融\n• 时间：定投分散买入时机\n\n经典配置参考（仅供参考）：\n• 保守型：股票30% + 债券60% + 现金10%\n• 平衡型：股票60% + 债券30% + 现金10%\n• 积极型：股票80% + 债券15% + 现金5%")
    );

    private static final String DEFAULT_REPLY = "感谢您的提问！作为金融助手，我可以为您解答关于以下方面的问题：\n\n• 股票投资与分析（A股、港股、美股）\n• 基金类型与选择（指数基金、主动基金、ETF）\n• 债券投资（国债、企业债、可转债）\n• 宏观经济与市场分析\n• 个人理财与资产配置\n\n请告诉我您具体想了解哪方面的内容，我会尽力为您详细解答。\n\n⚠️ 温馨提示：以上信息仅供参考，不构成投资建议，投资有风险，请谨慎决策。";

    public ChatResponse chat(ChatRequest request) {
        String message = request.getMessage();
        String reply = MOCK_ANSWERS.entrySet().stream()
                .filter(e -> message.contains(e.getKey()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(DEFAULT_REPLY);
        return new ChatResponse(reply);
    }
}
