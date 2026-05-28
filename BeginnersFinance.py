#Using only classes and objects before API
import random 

#Fake stock price:

stock_price=350

#Fake Company Score:

company_score=70
print('Company Score:',company_score)

class TechnicalAgent:
    def analyse(self,price):
        if price<200:
            return 'BUY'
        else:
            return 'SELL'
        
class SentimentAgent:

    def analyse(self):

        sentiments=["BUY", "SELL"]

        return random.choice(sentiments)
    
class FundamentalAgent:

    def analyse (self,company_score):
        if company_score>55:
            return 'BUY'
        else:
            return 'SELL'

class TraderAgent:

    def decide(self,signals):

        buy_count=signals.count('BUY')
        sell_count=signals.count('SELL')

        if buy_count>sell_count:
            return 'BUY'
        elif sell_count>buy_count:
            return 'SELL'
        else:
            return 'HOLD'
        
class RiskManager:

    def approve_trade(self, trader_dec, price):

        if price>300 and trader_dec=='BUY':
            return 'TRADE REJECTED'
        else:
            return 'TRADE APPROVED'




technical=TechnicalAgent()
sentiment=SentimentAgent()
fundamental=FundamentalAgent()
trader=TraderAgent()
risk=RiskManager()

tech_result = technical.analyse(stock_price)
sentiment_result = sentiment.analyse()
fundamental_result=fundamental.analyse(company_score)

signals=[tech_result, sentiment_result, fundamental_result]

trader_dec = trader.decide(signals)

risk_result=risk.approve_trade(trader_dec, stock_price)

print("Stock Price:", stock_price)

print("Technical:", tech_result)

print("Sentiment:", sentiment_result)

print("Fundamental:", fundamental_result)

print("Trader Decision:", trader_dec)

print("Risk Manager:", risk_result)
