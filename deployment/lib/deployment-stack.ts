import * as cdk from 'aws-cdk-lib'
import * as events from 'aws-cdk-lib/aws-events'
import * as lambda from 'aws-cdk-lib/aws-lambda'
import * as iam from 'aws-cdk-lib/aws-iam'
import * as ssm from 'aws-cdk-lib/aws-ssm'
import * as targets from 'aws-cdk-lib/aws-events-targets'
import { Construct } from 'constructs'
import { Effect } from 'aws-cdk-lib/aws-iam'
import { config } from './config/config'
import { Duration } from 'aws-cdk-lib'

export class DeploymentStack extends cdk.Stack {
  constructor(scope: Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props)
    const ssmParams: ssm.IParameter[] = []

    ssmParams.push(new ssm.StringListParameter(this, 'atis-send-to-addresses', {
      parameterName: '/atis/send-to-addresses',
      description: 'Email addresses to send notifications to',
      stringListValue: config.SendToEmails
    }))
    
    ssmParams.push(new ssm.StringParameter(this, 'atis-send-from-address', {
      parameterName: '/atis/send-from-address',
      description: 'Email address to show as the From address for notifications',
      stringValue: config.SendFromAddress
    }))

    ssmParams.push(new ssm.StringListParameter(this, 'atis-filter-keywords', {
      parameterName: '/atis/filter-keywords',
      description: 'Keywords to filter notifications again',
      stringListValue: config.FilterKeywords
    }))

    const handlerRole = new iam.Role(this, 'atis-handler-role', {
      assumedBy: new iam.ServicePrincipal("lambda.amazonaws.com"),
      inlinePolicies: {
        "SesPolicy": new iam.PolicyDocument({
          statements: [new iam.PolicyStatement({
            effect: Effect.ALLOW,
            actions: ["ses:SendEmail"],
            resources: ["*"]
          })]
        })
      }
    })

    ssmParams.forEach((it) => {it.grantRead(handlerRole)})

    const handler = new lambda.Function(this, 'atis-handler', {
      functionName: 'atis-handler',
      runtime: lambda.Runtime.JAVA_11,
      architecture: lambda.Architecture.ARM_64,
      memorySize: 512,
      timeout: Duration.minutes(1),
      code: lambda.Code.fromAsset("build/atis.jar"),
      handler: "MainKt::lambdaHandler",
      role: handlerRole
    })

    new events.Rule(this, 'schedule', {
      schedule: events.Schedule.cron({minute: '0', hour: '15', day: '*'}), // Run at 10a ET daily
      targets: [new targets.LambdaFunction(handler)]
    })
  }
}
